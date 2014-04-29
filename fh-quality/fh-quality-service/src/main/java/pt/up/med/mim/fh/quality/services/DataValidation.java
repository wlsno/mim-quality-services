package pt.up.med.mim.fh.quality.services;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import pt.up.med.mim.fh.quality.app.QualityModuleFlux;
import pt.up.med.mim.fh.quality.common.converter.DataSetBeanFactory;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterInstanceBean;
import pt.up.med.mim.fh.quality.domain.ws.input.FormInputData;
import pt.up.med.mim.fh.quality.domain.ws.ouput.FieldInstance;
import pt.up.med.mim.fh.quality.domain.ws.ouput.FormAssessmentOutput;
import pt.up.med.mim.fh.quality.domain.ws.ouput.FormDetails;
import pt.up.med.mim.fh.quality.domain.ws.ouput.FormField;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class DataValidation {

	QualityModuleFlux moduleflux = new QualityModuleFlux();

	@WebMethod(operationName = "validateForm")
	public FormAssessmentOutput validateForm(@WebParam(name= "inputData") FormInputData inputData) {

		if (inputData == null)
			return null;

		DataSetBean inputBean = beforeEvaluate(inputData);

		DataSetBean result = assessForm(inputBean);

		return convert(result);

	}

	private DataSetBean beforeEvaluate(FormInputData inputData) {
		DataSetBean inputBean = DataSetBeanFactory.convert(inputData);
		return inputBean;
	}

	private DataSetBean assessForm(DataSetBean inputBean) {
		DataSetBean bean = new DataSetBean();

		try {
			bean = moduleflux.evaluate(inputBean);
		} catch (BayesianNetworkException e) {
			e.printStackTrace();
		}
		return bean;
	}

	private FormAssessmentOutput convert(DataSetBean result) {

		FormAssessmentOutput serviceResult = new FormAssessmentOutput();
		FormDetails details = new FormDetails();
		details.setName(result.getArchtypeId());
		serviceResult.setDetails(details);

		for (ParameterBean parameter : result.getParameters()) {
			FormField field = new FormField();
			field.setAlias(parameter.getAlias());

//			if (parameter.getType().equals(QueryType.MPC)) {
//				ParameterInstanceBean instance = result.getParameterMPC(parameter.getAlias());
//				FieldInstance value = new FieldInstance();
//				value.setInstance(instance.getInstanceName());
//				value.setResult(instance.getConditionalProbability().toString());
//				
//				field.setInstances(Collections.singletonList(value));
//				
//			} else {
				List<FieldInstance> fields = new ArrayList<FieldInstance>();
				for (ParameterInstanceBean instance : parameter.getInstances()) {
					FieldInstance value = new FieldInstance();
					value.setInstance(instance.getInstanceName());
					value.setResult(instance.getConditionalProbability());
					fields.add(value);
				}
				field.setInstances(fields);
//			}

			serviceResult.addToFields(field);
		}

		return serviceResult;
	}
}
