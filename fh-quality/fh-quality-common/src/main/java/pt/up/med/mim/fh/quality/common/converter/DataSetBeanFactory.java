package pt.up.med.mim.fh.quality.common.converter;

import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterInstanceBean;
import pt.up.med.mim.fh.quality.domain.ws.input.FormInputData;
import pt.up.med.mim.fh.quality.domain.ws.input.InputField;

public abstract class DataSetBeanFactory {

	public static DataSetBean convert(FormInputData input) {
		if (input == null)
			return null;

		DataSetBean result = new DataSetBean();
		result.setAlgorithm(input.getAlgorithm());
		result.setArchtypeId(input.getFormID());
		result.setNoEvidenceMarker(input.getNoEvidenceMarker());

		if (input.getFields() == null)
			return null;

		for (InputField field : input.getFields()) {
			ParameterBean parameter = new ParameterBean();
			parameter.setAlias(field.getAlias());
			parameter.setOpenEHRpath(field.getPath() != null ? field.getPath(): field.getAlias());
			parameter.setHasEvidence(field.getHasEvidence());
//			parameter.setType(field.getResultType());
			
			if (field.getHasEvidence()) {
				ParameterInstanceBean instance = new ParameterInstanceBean(field.getValue(), Double.valueOf(1));
				parameter.addToInstances(instance);
				parameter.setEvidenceOnParameterInstance(field.getValue());
			}
			result.addToParameters(parameter);
		}
		
		return result;

	}

}
