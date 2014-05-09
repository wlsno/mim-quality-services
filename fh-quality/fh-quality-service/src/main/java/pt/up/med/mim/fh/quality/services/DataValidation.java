package pt.up.med.mim.fh.quality.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import pt.up.med.mim.fh.quality.app.QualityModuleFlux;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCase;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;
import pt.up.med.mim.fh.quality.services.base.IQualityService;
import pt.up.med.mim.fh.quality.services.base.QualityServiceBase;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class DataValidation extends QualityServiceBase implements IQualityService {

	QualityModuleFlux moduleflux = new QualityModuleFlux();

	@WebMethod(operationName = "validateForm")
	public OutputCase validateCase(@WebParam(name = "inputData") InputCase inputData) {
		DataSetBean bean = new DataSetBean();
		OutputCase result = new OutputCase();

		try {
			inferencePreAction(inputData, bean);
			bean = assessForm(bean);
			inferencePostAction(result, bean);
			return result;
		} catch (QualityServiceException e) {
			e.printStackTrace();
			return null;
		}

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

//	private FormAssessmentOutput convert(DataSetBean result) {
//
//		FormAssessmentOutput serviceResult = new FormAssessmentOutput();
//		FormDetails details = new FormDetails();
//		details.setName(result.getArchtypeId());
//		serviceResult.setDetails(details);
//
//		for (ParameterBean parameter : result.getParameters()) {
//			FormField field = new FormField();
//			field.setAlias(parameter.getAlias());
//
//			// if (parameter.getType().equals(QueryType.MPC)) {
//			// ParameterInstanceBean instance =
//			// result.getParameterMPC(parameter.getAlias());
//			// FieldInstance value = new FieldInstance();
//			// value.setInstance(instance.getInstanceName());
//			// value.setResult(instance.getConditionalProbability().toString());
//			//
//			// field.setInstances(Collections.singletonList(value));
//			//
//			// } else {
//			List<FieldInstance> fields = new ArrayList<FieldInstance>();
//			for (ParameterInstanceBean instance : parameter.getInstances()) {
//				FieldInstance value = new FieldInstance();
//				value.setInstance(instance.getInstanceName());
//				value.setResult(instance.getConditionalProbability());
//				fields.add(value);
//			}
//			field.setInstances(fields);
//			// }
//
//			serviceResult.addToFields(field);
//		}
//
//		return serviceResult;
//	}
}
