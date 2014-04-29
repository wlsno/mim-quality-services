package pt.up.med.mim.fh.quality.domain.inference.beans;

import java.io.Serializable;
import java.util.Comparator;

public class ParameterInstanceBean implements Serializable, Comparator<ParameterInstanceBean>, Comparable<ParameterInstanceBean> {

	private static final long serialVersionUID = 1L;
	
	private String instanceName;
	private Double conditionalProbability;
	private Boolean isEvidence = Boolean.FALSE;
	
	public ParameterInstanceBean() {
	}
	
	public ParameterInstanceBean(String instanceName, Double conditionalProbability) {
		this.setInstanceName(instanceName);
		this.setConditionalProbability(conditionalProbability);
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Double getConditionalProbability() {
		return conditionalProbability;
	}

	public void setConditionalProbability(Double conditionalProbability) {
		this.conditionalProbability = conditionalProbability;
	}

	public Boolean getIsEvidence() {
		return isEvidence;
	}
	
	public void setIsEvidence(Boolean isEvidence) {
		if(isEvidence == Boolean.TRUE)
			this.setConditionalProbability(new Double(1));
		
		this.isEvidence = isEvidence;
	}

	public int compareTo(ParameterInstanceBean o) {
		if(o == null || o.getConditionalProbability() == null)
			return -1;
		
		return o.getConditionalProbability().compareTo(this.getConditionalProbability());
	}

	public int compare(ParameterInstanceBean o1, ParameterInstanceBean o2) {
		return o2.getConditionalProbability().compareTo(o1.getConditionalProbability());
	}
	
}
