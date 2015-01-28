package pt.up.med.mim.fh.quality.services.base;

import java.util.List;

import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.service.domain.converters.InputCaseConverter;
import pt.up.med.mim.fh.quality.service.domain.converters.OutputCaseConverter;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceResult;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceRequest;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;
import pt.up.med.mim.fh.quality.service.domain.validators.CaseValidationEngine;

public abstract class QualityServiceBase {
	
	protected void inferencePreAction(DataEvaluationServiceRequest inputCase, final DataSetBean bean) throws QualityServiceException{
		List<String> messages = CaseValidationEngine.validate(inputCase);
		
		if (!messages.isEmpty())
			throw new QualityServiceException();
		
		new InputCaseConverter().convert(inputCase, bean);
	}
	
	protected void inferencePostAction(DataEvaluationServiceResult output, DataSetBean bean) throws QualityServiceException {
		new OutputCaseConverter().convertBack(bean, output);
	}
}
