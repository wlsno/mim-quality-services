package pt.up.med.mim.fh.quality.service.domain.converters;

import java.util.ArrayList;

import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterInstanceBean;
import pt.up.med.mim.fh.quality.domain.inference.common.InferenceAlgorithm;
import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCaseConfig;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCaseData;
import pt.up.med.mim.fh.quality.service.domain.entities.InputParameter;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public class InputCaseConverter implements IServiceObjectConverter {

	/**
	 * Converts the given InputCase into the DataSetBean
	 */
	public void convert(IServiceObject serviceObject, final DataSetBean bean) throws QualityServiceException {
		InputCase inputCase = null;
		
		try {
			inputCase = (InputCase) serviceObject;
			InputCaseData caseData = inputCase.getData();
			
			convertConfiguration(bean, inputCase.getConfiguration());
			convertData(bean, caseData);
			
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
	
	private void convertData(final DataSetBean bean, InputCaseData data){
		bean.setArchtypeId(data.getId());
		
		for (InputParameter param: data.getParameters()){
			bean.addToParameters(convertParameter(param));
			if (param.getIsEvidence() == Boolean.TRUE)
				bean.setEvidenceToParameter(param.getId(), param.getValue());
		}
	}
	
	private ParameterBean convertParameter(InputParameter inputParameter) {
		ParameterBean parameterBean = new ParameterBean();
		
		parameterBean.setAlias(inputParameter.getId());
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
		try {
			InputCase inputCase = new InputCase();
			convertBackConfiguration(datasetBean, inputCase);
			convertBackData(datasetBean, inputCase);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QualityServiceException("");
		}
	}
	
	private void convertBackData(DataSetBean datasetBean, final InputCase inputCase) {
		InputCaseData data = new InputCaseData();
		data.setId(datasetBean.getArchtypeId());
		data.setParameters(new ArrayList<InputParameter>());
		for (ParameterBean bParam : datasetBean.getParameters()){
			InputParameter parameter = new InputParameter(bParam.getAlias(), bParam.getOpenEHRpath(), bParam.getMPC().getInstanceName(), bParam.getHasEvidence());
			data.getParameters().add(parameter);
		}
	}

	private void convertBackConfiguration(DataSetBean datasetBean, final InputCase inputCase) {
		InputCaseConfig configuration = new InputCaseConfig();
		configuration.setAlgorithm(pt.up.med.mim.fh.quality.service.domain.entities.InferenceAlgorithm.valueOf(datasetBean.getAlgorithm().name()));
		configuration.setNoEvidenceMark(datasetBean.getNoEvidenceMarker());
		
		inputCase.setConfiguration(configuration);

	}
}
