package pt.up.med.mim.fh.quality.service.domain.validators;

import java.util.List;

import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public interface IServiceObjectValidator {
	
	 List<String> validate(IServiceObject serviceObject) throws QualityServiceException;
}
