package pt.up.med.mim.fh.quality.services.base;

import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceResult;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceRequest;

public interface IQualityService {
	
	DataEvaluationServiceResult validateCase(DataEvaluationServiceRequest inputData);
}
