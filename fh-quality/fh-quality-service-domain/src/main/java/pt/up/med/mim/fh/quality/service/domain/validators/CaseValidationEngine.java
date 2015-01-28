package pt.up.med.mim.fh.quality.service.domain.validators;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceRequest;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public abstract class CaseValidationEngine {
	private static ConfigurationValidator CONFIG = new ConfigurationValidator();
	private static DataValidator DATA = new DataValidator();
	private static HashMap<String, List<IServiceObjectValidator>> validators = new HashMap<String, List<IServiceObjectValidator>>();
	
	static {
		validators.put(DataEvaluationServiceRequest.class.getName(), Arrays.asList(CONFIG, DATA));
	}
	
	public static List<String> validate(IServiceObject serviceObject) throws QualityServiceException {
		List<IServiceObjectValidator> entry = validators.get(serviceObject.getClass().getName());
		
		for (IServiceObjectValidator validator : entry) {
			List<String> messages = validator.validate(serviceObject);
			if (!messages.isEmpty())
				return messages;
		}
		return Collections.emptyList();
	}

}
