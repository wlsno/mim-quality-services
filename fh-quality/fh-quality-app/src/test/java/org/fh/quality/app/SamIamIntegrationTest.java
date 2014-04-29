package org.fh.quality.app;

import pt.up.med.mim.fh.quality.app.QualityModuleFlux;
import pt.up.med.mim.fh.quality.common.converter.DataSetBeanFactory;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.common.InferenceAlgorithm;
//import pt.up.med.mim.fh.quality.domain.inference.common.QueryType;
import pt.up.med.mim.fh.quality.domain.ws.input.FormInputData;
import pt.up.med.mim.fh.quality.domain.ws.input.InputField;
import pt.up.med.mim.fh.quality.domain.ws.ouput.FormAssessmentOutput;
import pt.up.med.mim.fh.quality.domain.ws.ouput.FormField;

public class SamIamIntegrationTest {
	private static FormInputData form = null;
	private static String formId = "adverseReaction";
	private static char noEvidenceMarker = '?';

	public static void main(String[] args) {
		buildInputData();
		
		DataSetBean inputBean = DataSetBeanFactory.convert(form);
		QualityModuleFlux validation = new QualityModuleFlux();
		try {
			DataSetBean output = validation.evaluate(inputBean);
		} catch (BayesianNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//printPrettyOutput(output);
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
