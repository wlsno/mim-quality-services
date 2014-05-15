package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class OutputCaseDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private Float probability;
	private Float inferenceTime;
	
	public OutputCaseDetails() {
		super();
	}

	public Float getProbability() {
		return probability;
	}
	public void setProbability(Float probability) {
		this.probability = probability;
	}

	public Float getInferenceTime() {
		return inferenceTime;
	}
	public void setInferenceTime(Float inferenceTime) {
		this.inferenceTime = inferenceTime;
	}
	
}
