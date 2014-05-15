package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class InputCaseConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private InferenceAlgorithm algorithm = InferenceAlgorithm.LOOPYBELIEFPROPAGATION;
	private String noEvidenceMark;
	
	public InputCaseConfig() {
		super();
	}

	public InferenceAlgorithm getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(InferenceAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public String getNoEvidenceMark() {
		return noEvidenceMark;
	}
	public void setNoEvidenceMark(String noEvidenceMark) {
		this.noEvidenceMark = noEvidenceMark;
	}

}
