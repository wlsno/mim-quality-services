package pt.up.med.mim.fh.quality.service.domain.converters;

import java.util.ArrayList;
import java.util.List;

import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterInstanceBean;
import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCaseData;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCaseDetails;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCategory;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputParameter;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public class OutputCaseConverter implements IServiceObjectConverter {

	/**
	 * Converts the given OutpuCase into the DataSetBean
	 */
	public void convert(IServiceObject serviceObject, final DataSetBean bean) throws QualityServiceException {
		OutputCase outputCase = null;
		
		try {
			outputCase = (OutputCase)serviceObject;
			convertData(bean, outputCase.getData());
			
		} catch (Exception e){
			throw new QualityServiceException();
		}

	}
		
	private void convertDetails(final DataSetBean bean, OutputCaseDetails details) {
		
	}
	
	private void convertData(final DataSetBean bean, OutputCaseData data){
		bean.setArchtypeId(data.getId());
		
		for (OutputParameter parameter : data.getParameters()) {
			bean.addToParameters(convertParameter(parameter));
		}
		
	}
		
	private ParameterBean convertParameter(OutputParameter caseParameter){
		ParameterBean parameterBean = new ParameterBean();
		
		parameterBean.setAlias(caseParameter.getId());
		for (OutputCategory category : caseParameter.getResults()) {
			ParameterInstanceBean instance = new ParameterInstanceBean(category.getName(), category.getProbability());
			parameterBean.addToInstances(instance);
		}
		
		return parameterBean;
	}
	
	/**
	 * Converts the given DataSetBean into the OutputCase
	 */
	public void convertBack(DataSetBean datasetBean, final IServiceObject serviceObject) throws QualityServiceException {
		
		try {
			convertBackData(datasetBean, serviceObject);
		} catch (Exception e) {
			throw new QualityServiceException();
		}
	}
	
	private void convertBackData(DataSetBean bean, IServiceObject outputCase){
		OutputCaseData data = new OutputCaseData();
		data.setId(bean.getArchtypeId());
		
		List<OutputParameter> parameters = new ArrayList<OutputParameter>();
		for (ParameterBean parameter : bean.getParameters()) {
			parameters.add(convertBackParameter(parameter));
		}
		data.setParameters(parameters);
		((OutputCase)outputCase).setData(data);
	}
	
	private OutputParameter convertBackParameter(ParameterBean parameter){
		OutputParameter caseParameter = new OutputParameter();
		caseParameter.setId(parameter.getAlias());
		caseParameter.setResults(new ArrayList<OutputCategory>());
		
		for (ParameterInstanceBean instance : parameter.getInstances()) {
			OutputCategory category = new OutputCategory(instance.getInstanceName(), instance.getConditionalProbability());
			caseParameter.getResults().add(category);
		}
		
		caseParameter.setMostProbableCategory(new OutputCategory(parameter.getMPC().getInstanceName(), parameter.getMPC().getConditionalProbability()));
		return caseParameter;
		
	}
}
