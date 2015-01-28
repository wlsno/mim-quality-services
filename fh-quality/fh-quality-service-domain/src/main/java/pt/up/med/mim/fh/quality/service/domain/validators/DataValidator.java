package pt.up.med.mim.fh.quality.service.domain.validators;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import pt.up.med.mim.fh.quality.service.domain.entities.Category;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceResult;
import pt.up.med.mim.fh.quality.service.domain.entities.EvaluatedCaseField;
import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceRequest;
import pt.up.med.mim.fh.quality.service.domain.entities.InputField;
import pt.up.med.mim.fh.quality.service.domain.entities.ResponseBody;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public class DataValidator implements IServiceObjectValidator {
	
	/**
	 * Validates the IServiceObject
	 * @param inputcase
	 * @return A list of errors or a empty list if no errors were found
	 */
	public List<String> validate(IServiceObject serviceObject) throws QualityServiceException {
		
		try {
			if (serviceObject instanceof DataEvaluationServiceRequest){
				return validateInputData((DataEvaluationServiceRequest)serviceObject);
			} else if (serviceObject instanceof DataEvaluationServiceResult){
				return validateOutputData((DataEvaluationServiceResult)serviceObject);
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
	private List<String> validateInputData(DataEvaluationServiceRequest inputCase) {
		
		if(!checkParameters(inputCase.getBody().getFields(), inputCase.getBody().getRequestDetails().getNoEvidenceMark()))
			return Collections.singletonList("");
		
		return Collections.emptyList();
	}
	
	/**
	 * 
	 * @param parameters - The parameter list to check
	 * @return
	 */
	private boolean checkParameters(List<InputField> parameters, String noEvidenceMarker){
		boolean compare2marker = (noEvidenceMarker != null);
		
		// false se a lista de parametros estiver vazia
		if (parameters == null || parameters.isEmpty())
			return false;
		
		for (InputField inputParameter : parameters) {
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

	private List<String> validateOutputData(DataEvaluationServiceResult output){
		
		if (output.getBody() == null || checkDataHeader(output.getBody())){
			Collections.singletonList("");
		}
		
		if (hasInvalidParameter(output.getBody().getResult().getEvaluatedFields())){
			Collections.singletonList("");
		}
		
		return Collections.emptyList();
	}
	
	private boolean checkDataHeader(ResponseBody data){
		return StringUtils.isBlank(data.getResult().getCaseDetail().getForm().getCode());
	}
	
	private boolean hasInvalidParameter(List<EvaluatedCaseField> fields){
		if (CollectionUtils.isEmpty(fields))
			return Boolean.TRUE;
		
		for (EvaluatedCaseField field : fields) {
			if (StringUtils.isBlank(field.getField().getCode())) {
				return Boolean.TRUE;
			}
			
			if (CollectionUtils.isEmpty(field.getCatetogies())){
				return Boolean.TRUE;
			}
			
			for (Category category : field.getCatetogies()) {
				if (StringUtils.isBlank(category.getDescription()))
					return Boolean.TRUE;
				
				if (category.getProbability() == null)
					return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}

}
