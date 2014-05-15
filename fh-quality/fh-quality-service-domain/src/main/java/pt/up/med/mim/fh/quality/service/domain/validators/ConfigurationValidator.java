package pt.up.med.mim.fh.quality.service.domain.validators;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.entities.InferenceAlgorithm;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCase;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public class ConfigurationValidator implements IServiceObjectValidator {

	//public ConfigurationValidator() { }

	/**
	 * Validates the IServiceObject
	 * @param inputcase
	 * @return A list of errors or a empty list if no errors were found
	 */
	public List<String> validate(IServiceObject serviceObject) throws QualityServiceException {
		try {
			if (serviceObject instanceof InputCase){
				return validateInputData((InputCase)serviceObject);
			} else if (serviceObject instanceof OutputCase){
				return validateOutputData((OutputCase)serviceObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new QualityServiceException();
		}
		
		return Collections.emptyList();
	}
	
	/**
	 * Input Case validator method
	 * @param inputcase
	 * @return A list of errors or a empty list if no errors were found
	 */
	private List<String> validateInputData(InputCase inputcase) {
		if (inputcase == null || inputcase.getConfiguration() == null || inputcase.getData() == null){
			//Inputcase a null
			//TODO: implementar o método de obter mensagem dos resources
			return Collections.singletonList("");
		}
		
		if (checkInvalidFormId(inputcase.getData().getId())){
			return Collections.singletonList("");
		}
		
		if (checkAlgorithm(inputcase.getConfiguration().getAlgorithm())){
			return Collections.singletonList("");
		}
		
		return Collections.emptyList();
	}
	
	/**
	 * Checks if the input case form id is null or blank
	 * @param formID - The form id to check
	 * @return true if the string is null or blank
	 */
	private boolean checkInvalidFormId(String formID){
		return StringUtils.isBlank(formID);
	}
	
	/**
	 * Checks if the algorithm is null
	 * @param algorithm - The algorithm to check
	 * @return true if the algorithm is null
	 */
	private boolean checkAlgorithm(InferenceAlgorithm algorithm) {
		return algorithm == null;
	}
	
	
	
	private List<String> validateOutputData(OutputCase serviceObject) {
		return Collections.emptyList();
	}
	

}
