package pt.up.med.mim.fh.quality.service.domain.converters;

import java.util.ArrayList;

import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterInstanceBean;
import pt.up.med.mim.fh.quality.domain.inference.common.InferenceAlgorithm;
import pt.up.med.mim.fh.quality.service.domain.entities.GenericCode;
import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceRequest;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCaseConfig;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCaseDetail;
import pt.up.med.mim.fh.quality.service.domain.entities.InputField;
import pt.up.med.mim.fh.quality.service.domain.entities.RequestBody;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public class InputCaseConverter implements IServiceObjectConverter {

	/**
	 * Converts the given InputCase into the DataSetBean
	 */
	public void convert(IServiceObject serviceObject, final DataSetBean bean) throws QualityServiceException {
		DataEvaluationServiceRequest inputCase = null;
		
		try {
			inputCase = (DataEvaluationServiceRequest) serviceObject;
			InputCaseDetail caseData = inputCase.getBody().getCaseDetail();
			
			convertConfiguration(bean, inputCase.getBody().getRequestDetails());
			convertData(bean, inputCase.getBody());
			
		} catch (Exception e){
			e.printStackTrace();
			// TODO: Implmentar a pesquisa de strings por resouces
			throw new QualityServiceException("");
		}
	}
	
	private void convertConfiguration(final DataSetBean bean, InputCaseConfig config){
		bean.setAlgorithm(InferenceAlgorithm.valueOf(config.getAlgorithm().name()));
		bean.setNoEvidenceMarker(config.getNoEvidenceMark());
	}
	
	private void convertData(final DataSetBean bean, RequestBody body){
		bean.setArchtypeId(body.getCaseDetail().getFormIdentifier().getCode());
		
		for (InputField param: body.getFields()){
			bean.addToParameters(convertParameter(param));
			if (param.getIsEvidence() == Boolean.TRUE)
				bean.setEvidenceToParameter(param.getField().getCode(), param.getValue());
		}
	}
	
	private ParameterBean convertParameter(InputField inputParameter) {
		ParameterBean parameterBean = new ParameterBean();
		
		parameterBean.setAlias(inputParameter.getField().getCode());
		parameterBean.setHasEvidence(inputParameter.getIsEvidence());
		
		if (inputParameter.getIsEvidence() == Boolean.TRUE){
			ParameterInstanceBean instance = new ParameterInstanceBean(inputParameter.getValue(), Double.valueOf(1));
			parameterBean.addToInstances(instance);
		}
		return parameterBean;
	}
	
	/**
	 * Converts the given DataSetBean into the InputCase
	 */
	public void convertBack(DataSetBean datasetBean, final IServiceObject inputData) throws QualityServiceException {
//		try {
//			DataEvaluationServiceRequest inputCase = new DataEvaluationServiceRequest();
//			convertBackConfiguration(datasetBean, inputCase);
//			convertBackData(datasetBean, inputCase);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new QualityServiceException("");
//		}
	}
	
	private void convertBackData(DataSetBean datasetBean, final DataEvaluationServiceRequest inputCase) {
//		InputCaseDetail data = new InputCaseDetail();
//		data.setFormIdentifier(new GenericCode(datasetBean.getArchtypeId(), null));
//		data.setFields(new ArrayList<InputField>());
//		for (ParameterBean bParam : datasetBean.getParameters()){
//			InputField parameter = new InputField(bParam.getAlias(), bParam.getOpenEHRpath(), bParam.getMPC().getInstanceName(), bParam.getHasEvidence());
//			data.getParameters().add(parameter);
//		}
	}

	private void convertBackConfiguration(DataSetBean datasetBean, final DataEvaluationServiceRequest inputCase) {
//		InputCaseConfig configuration = new InputCaseConfig();
//		configuration.setAlgorithm(pt.up.med.mim.fh.quality.service.domain.entities.InferenceAlgorithm.valueOf(datasetBean.getAlgorithm().name()));
//		configuration.setNoEvidenceMark(datasetBean.getNoEvidenceMarker());
//		
//		inputCase.setConfiguration(configuration);

	}
}
