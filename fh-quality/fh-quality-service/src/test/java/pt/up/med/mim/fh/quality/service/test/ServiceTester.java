package pt.up.med.mim.fh.quality.service.test;

import java.util.ArrayList;
import java.util.List;

import pt.up.med.mim.fh.quality.service.domain.entities.InferenceAlgorithm;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCaseConfig;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCaseData;
import pt.up.med.mim.fh.quality.service.domain.entities.InputParameter;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCategory;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputParameter;
import pt.up.med.mim.fh.quality.services.DataValidation;

public class ServiceTester {

	private static InputCase form = null;
	private static String formId = "adverseReaction";
	private static String noEvidenceMarker = "?";

	public static void main(String[] args) {
		buildInputData();
		
		DataValidation validation = new DataValidation();
		OutputCase output = validation.validateCase(form);
		
		printPrettyOutput(output);
	}

	private static void buildInputData() {
		form = new InputCase();
		
		InputCaseData data = new InputCaseData();
		data.setId(formId);
		form.setData(data);
		
		buildInputParameters();	
		
		buildConfiguration();
	}
	
	private static void buildConfiguration() {
		InputCaseConfig config = new InputCaseConfig();
		config.setAlgorithm(InferenceAlgorithm.LOOPYBELIEFPROPAGATION);
		config.setNoEvidenceMark(noEvidenceMarker);
		
		form.setConfiguration(config);
		
	}

	private static void buildInputParameters() {
		
		List<InputParameter> parameters = new ArrayList<InputParameter>();
		parameters.add(new InputParameter("Described", "Described", "No", Boolean.TRUE));
		parameters.add(new InputParameter("Concomitant", "Concomitant", "No", Boolean.TRUE));
		parameters.add(new InputParameter("Suspended", "Suspended", "Yes", Boolean.TRUE));
		parameters.add(new InputParameter("Reintroduced", "Reintroduced", "No", Boolean.TRUE));
		parameters.add(new InputParameter("Reappeared", "Reappeared", "NA", Boolean.TRUE));
		parameters.add(new InputParameter("Notifier", "Notifier", "Nurse", Boolean.TRUE));
		parameters.add(new InputParameter("ImprovedAfterSuspension", "ImprovedAfterSuspension", "NA", Boolean.TRUE));
		parameters.add(new InputParameter("Administration", "Administration", "Injectable", Boolean.TRUE));
		parameters.add(new InputParameter("SuspectedInteraction", "SuspectedInteraction", "No", Boolean.TRUE));
		parameters.add(new InputParameter("PharmaGroup", "PharmaGroup", "GastrointestinalSystem", Boolean.TRUE));
		
		parameters.add(new InputParameter("Probable", "Probable", "?", Boolean.FALSE));
		parameters.add(new InputParameter("Conditional", "Conditional", "?", Boolean.FALSE));
		parameters.add(new InputParameter("Definite", "Definite", "?", Boolean.FALSE));
		parameters.add(new InputParameter("Possible", "Possible", "?", Boolean.FALSE));
		
		form.getData().setParameters(parameters);
		
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

	private static void printPrettyOutput(OutputCase output){
		System.out.println("Resultado:");
		System.out.println(String.format("Rede Bayesiana: %s", output.getData().getId()));
		System.out.println();
		
		for (OutputParameter parameter : output.getData().getParameters()) {
			System.out.println(String.format("Parametro: %s", parameter.getId()));
			System.out.println(String.format("\tMPC: %s (%s)", parameter.getMostProbableCategory().getName(), parameter.getMostProbableCategory().getProbability()));
			System.out.println("\tCatergorias");
			
			for (OutputCategory category : parameter.getResults()) {
				System.out.println(String.format("\t\t%s (%s)", category.getName(), category.getProbability()));
				
			}
		}

		System.out.println();
	}
}
