package pt.up.med.mim.fh.quality.service.test;

import pt.up.med.mim.fh.quality.domain.inference.common.InferenceAlgorithm;
import pt.up.med.mim.fh.quality.domain.ws.input.FormInputData;
import pt.up.med.mim.fh.quality.domain.ws.input.InputField;
import pt.up.med.mim.fh.quality.domain.ws.ouput.FormAssessmentOutput;
import pt.up.med.mim.fh.quality.domain.ws.ouput.FormField;
import pt.up.med.mim.fh.quality.services.DataValidation;

public class ServiceTester {

	private static FormInputData form = null;
	private static String formId = "adverseReaction";
	private static char noEvidenceMarker = '?';

	public static void main(String[] args) {
		buildInputData();
		
		DataValidation validation = new DataValidation();
		FormAssessmentOutput output = validation.validateForm(form);
		
		printPrettyOutput(output);
	}

	private static void buildInputData() {
		form = new FormInputData();

		form.setAlgorithm(InferenceAlgorithm.LOOPYBELIEFPROPAGATION);
		form.setFormID(formId);
		form.setNoEvidenceMarker(noEvidenceMarker);
		
		buildInputParameters();	
	}

	private static void buildInputParameters() {
		
		form.addField(new InputField("Described", "Described", "No", Boolean.TRUE));
		form.addField(new InputField("Concomitant", "Concomitant", "No", Boolean.TRUE));
		form.addField(new InputField("Suspended", "Suspended", "Yes", Boolean.TRUE));
		form.addField(new InputField("Reintroduced", "Reintroduced", "No", Boolean.TRUE));
		form.addField(new InputField("Reappeared", "Reappeared", "NA", Boolean.TRUE));
		form.addField(new InputField("Notifier", "Notifier", "Nurse", Boolean.TRUE));
		form.addField(new InputField("ImprovedAfterSuspension", "ImprovedAfterSuspension", "NA", Boolean.TRUE));
		form.addField(new InputField("Administration", "Administration", "Injectable", Boolean.TRUE));
		form.addField(new InputField("SuspectedInteraction", "SuspectedInteraction", "No", Boolean.TRUE));
		form.addField(new InputField("PharmaGroup", "PharmaGroup", "GastrointestinalSystem", Boolean.TRUE));
		
		form.addField(new InputField("Probable", "Probable", "?", Boolean.FALSE));
		form.addField(new InputField("Conditional", "Conditional", "?", Boolean.FALSE));
		form.addField(new InputField("Definite", "Definite", "?", Boolean.FALSE));
		form.addField(new InputField("Possible", "Possible", "?", Boolean.FALSE));
		
//		form.addField(new InputField("Described", "Described", "Yes", Boolean.TRUE));
//		form.addField(new InputField("Concomitant", "Concomitant", "Yes", Boolean.TRUE));
//		form.addField(new InputField("Suspended", "Suspended", "Yes", Boolean.TRUE));
//		form.addField(new InputField("Reintroduced", "Reintroduced", "No", Boolean.TRUE));
//		form.addField(new InputField("Reappeared", "Reappeared", "NA", Boolean.TRUE));
//		form.addField(new InputField("Notifier", "Notifier", "Physician", Boolean.TRUE));
//		form.addField(new InputField("ImprovedAfterSuspension", "ImprovedAfterSuspension", "Yes", Boolean.TRUE));
//		form.addField(new InputField("Administration", "Administration", "Oral", Boolean.TRUE));
//		form.addField(new InputField("SuspectedInteraction", "SuspectedInteraction", "No", Boolean.TRUE));
//		form.addField(new InputField("PharmaGroup", "PharmaGroup", "DrugsForSkinDisorders", Boolean.TRUE));
//		
//		form.addField(new InputField("Probable", "Probable", "?", Boolean.FALSE));
//		form.addField(new InputField("Conditional", "Conditional", "?", Boolean.FALSE));
//		form.addField(new InputField("Definite", "Definite", "?", Boolean.FALSE));
//		form.addField(new InputField("Possible", "Possible", "?", Boolean.FALSE));
	}

	private static void printPrettyOutput(FormAssessmentOutput output){
		System.out.println("Resultado");
		System.out.println(String.format("Rede Bayesiana: %s", output.getDetails().getName()));
		System.out.println();
		System.out.println("== Evidência ==");
		
		for(FormField field : output.getData()){
			System.out.println(String.format("\t%s: %s (%s)", field.getAlias(),field.getInstances().get(0).getInstance(), field.getInstances().get(0).getResult()));
		}
		System.out.println();
		System.out.println("== Inferencia ==");
	}
}
