package pt.up.med.mim.fh.quality.service.domain.converters;

import java.util.ArrayList;
import java.util.List;

import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterInstanceBean;
import pt.up.med.mim.fh.quality.service.domain.entities.Category;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceResult;
import pt.up.med.mim.fh.quality.service.domain.entities.EvaluatedCase;
import pt.up.med.mim.fh.quality.service.domain.entities.EvaluatedCaseDetail;
import pt.up.med.mim.fh.quality.service.domain.entities.EvaluatedCaseField;
import pt.up.med.mim.fh.quality.service.domain.entities.GenericCode;
import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.entities.InferenceAlgorithm;
import pt.up.med.mim.fh.quality.service.domain.entities.ResponseBody;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public class OutputCaseConverter implements IServiceObjectConverter {

	/**
	 * Converts the given OutpuCase into the DataSetBean
	 */
	public void convert(IServiceObject serviceObject, final DataSetBean bean) throws QualityServiceException {
		DataEvaluationServiceResult outputCase = null;
		
		try {
			outputCase = (DataEvaluationServiceResult)serviceObject;
			convertData(bean, outputCase.getBody());
			
		} catch (Exception e){
			throw new QualityServiceException();
		}

	}
		
	private void convertDetails(final DataSetBean bean, ResponseBody details) {
		
	}
	
	private void convertData(final DataSetBean bean, ResponseBody data){
		bean.setArchtypeId(data.getResult().getCaseDetail().getForm().getCode());
		
		for (EvaluatedCaseField parameter : data.getResult().getEvaluatedFields()) {
			bean.addToParameters(convertParameter(parameter));
		}
		
	}
		
	private ParameterBean convertParameter(EvaluatedCaseField caseParameter){
		ParameterBean parameterBean = new ParameterBean();
		
		parameterBean.setAlias(caseParameter.getField().getCode());
		for (Category category : caseParameter.getCatetogies()) {
			ParameterInstanceBean instance = new ParameterInstanceBean(category.getDescription(), category.getProbability());
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
		if (outputCase == null)
			outputCase = new DataEvaluationServiceResult();
		
		((DataEvaluationServiceResult)outputCase).setBody(convertToResponseBody(bean));
	}
	
	private ResponseBody convertToResponseBody(DataSetBean bean){
		ResponseBody body = new ResponseBody();
		EvaluatedCase evaluatedCase = new EvaluatedCase();
		EvaluatedCaseDetail caseDetail = new EvaluatedCaseDetail();
		
		caseDetail.setForm(new GenericCode(bean.getArchtypeId(), null));
		caseDetail.setAlgorithm(InferenceAlgorithm.valueOf(bean.getAlgorithm().toString()));
		
		List<EvaluatedCaseField> fields = new ArrayList<EvaluatedCaseField>();
		for (ParameterBean field : bean.getParameters()) {
			fields.add(convertBackField(field));
		}
		
		evaluatedCase.setCaseDetail(caseDetail);
		evaluatedCase.setEvaluatedFields(fields);
		body.setResult(evaluatedCase);
		return body;
	}
	
	private EvaluatedCaseField convertBackField(ParameterBean parameter){
		EvaluatedCaseField caseParameter = new EvaluatedCaseField();
		List<Category> categories = new ArrayList<Category>();
		
		caseParameter.setField(new GenericCode(parameter.getAlias(), parameter.getOpenEHRpath()));
		
		for (ParameterInstanceBean instance : parameter.getInstances()) {
			Category category = new Category(instance.getInstanceName(), instance.getConditionalProbability());
			categories.add(category);
		}
		
		caseParameter.setCategories(categories);
		caseParameter.setMostProbableCategory(new Category(parameter.getMPC().getInstanceName(), parameter.getMPC().getConditionalProbability()));
		return caseParameter;
		
	}
}
