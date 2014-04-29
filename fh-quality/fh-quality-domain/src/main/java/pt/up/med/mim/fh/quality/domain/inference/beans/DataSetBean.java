package pt.up.med.mim.fh.quality.domain.inference.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import pt.up.med.mim.fh.quality.domain.inference.common.InferenceAlgorithm;

public class DataSetBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String archtypeId;
	private Character noEvidenceMarker;
	private InferenceAlgorithm algorithm;
	private List<ParameterBean> parameters;

	public DataSetBean() {
		super();
	}

	public DataSetBean(List<ParameterBean> parameters) {
		this.setParameters(parameters);
	}

	public String getArchtypeId() {
		return archtypeId;
	}

	public void setArchtypeId(String archtypeId) {
		this.archtypeId = archtypeId;
	}

	public Character getNoEvidenceMarker() {
		return noEvidenceMarker;
	}

	public void setNoEvidenceMarker(Character noEvidenceMarker) {
		this.noEvidenceMarker = noEvidenceMarker;
	}

	public InferenceAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(InferenceAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public List<ParameterBean> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterBean> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Adds the parameter to the parameter list
	 * 
	 * @param parameter
	 *            - The parameter to add
	 * @return true if the parameter was added or false if the parameter already
	 *         exists
	 */
	public boolean addToParameters(ParameterBean parameter) {
		if (CollectionUtils.isEmpty(getParameters()))
			this.parameters = new ArrayList<ParameterBean>();

		if (getParameters().contains(parameter))
			return false;

		getParameters().add(parameter);
		return true;
	}

	public void setEvidenceToParameter(String alias, String instanceName) {
		for (ParameterBean parameter : this.getParameters()) {
			if (parameter.getAlias().equalsIgnoreCase(alias))
				parameter.setEvidenceOnParameterInstance(instanceName);
		}
	}

	/**
	 * Gets the Most Probable Category for the the searched parameter
	 * 
	 * @param parameterName
	 *            - The name of the wanted parameter
	 * @return The Most Probable Category for the the searched parameter or null
	 *         if the parameter doesn't exist or it doesn's have categories
	 */
	public ParameterInstanceBean getParameterMPC(final String parameterName) {
		ParameterBean param = getParameter(parameterName);

		if (param == null)
			return null;

		return param.getMPC();
	}

	public ParameterBean getParameter(final String parameterName) {
		return (ParameterBean) CollectionUtils.find(parameters,	new Predicate() {
			public boolean evaluate(Object object) {
				return ((ParameterBean) object).getAlias().equalsIgnoreCase(parameterName);
			}
		});
	}

}
