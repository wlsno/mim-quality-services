package pt.up.med.mim.fh.quality.service.domain.validators;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.entities.InputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.InputParameter;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCaseData;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCategory;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputParameter;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public class DataValidator implements IServiceObjectValidator {
	
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
	private List<String> validateInputData(InputCase inputCase) {
		
		if(!checkParameters(inputCase.getData().getParameters(), inputCase.getConfiguration().getNoEvidenceMark()))
			return Collections.singletonList("");
		
		return Collections.emptyList();
	}
	
	/**
	 * 
	 * @param parameters - The parameter list to check
	 * @return
	 */
	private boolean checkParameters(List<InputParameter> parameters, String noEvidenceMarker){
		boolean compare2marker = (noEvidenceMarker != null);
		
		// false se a lista de parametros estiver vazia
		if (parameters == null || parameters.isEmpty())
			return false;
		
		for (InputParameter inputParameter : parameters) {
			// false se foi indicado o indicador de falta de evidencia e o valor for null
			if(compare2marker){
				if (StringUtils.isBlank(inputParameter.getValue()))
					return false;
			} else {
				// false se a flag de evidencia estiver a null
				if(inputParameter.getIsEvidence() == null){
					return false;
					// false se a flag de evidencia estiver a true e o valor for null
				} else if (inputParameter.getIsEvidence()) {
					if (StringUtils.isBlank(inputParameter.getValue()))
						return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

	private List<String> validateOutputData(OutputCase output){
		
		if (output.getData() == null || checkDataHeader(output.getData())){
			Collections.singletonList("");
		}
		
		if (hasInvalidParameter(output.getData().getParameters())){
			Collections.singletonList("");
		}
		
		return Collections.emptyList();
	}
	
	private boolean checkDataHeader(OutputCaseData data){
		return StringUtils.isBlank(data.getId());
	}
	
	private boolean hasInvalidParameter(List<OutputParameter> parameters){
		if (CollectionUtils.isEmpty(parameters))
			return Boolean.TRUE;
		
		for (OutputParameter outputParameter : parameters) {
			if (StringUtils.isBlank(outputParameter.getId())) {
				return Boolean.TRUE;
			}
			
			if (CollectionUtils.isEmpty(outputParameter.getResults())){
				return Boolean.TRUE;
			}
			
			for (OutputCategory category : outputParameter.getResults()) {
				if (StringUtils.isBlank(category.getName()))
					return Boolean.TRUE;
				
				if (category.getProbability() == null)
					return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}

}
