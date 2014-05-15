package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class InputParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String openEHRPath;
	private String value;
	
	private Boolean isEvidence;
	private PretendedResult pretendedResult = PretendedResult.ALL_CATEGORIES;

	public InputParameter() {
		super();
	}

	public InputParameter(String id, String openEHRPath, String value, Boolean isEvidence) {
		this.setId(id);
		this.setOpenEHRPath(openEHRPath);
		this.setValue(value);
		this.setIsEvidence(isEvidence);
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getOpenEHRPath() {
		return openEHRPath;
	}
	public void setOpenEHRPath(String openEHRPath) {
		this.openEHRPath = openEHRPath;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getIsEvidence() {
		return isEvidence;
	}
	public void setIsEvidence(Boolean isEvidence) {
		this.isEvidence = isEvidence;
	}

	public PretendedResult getPretendedResult() {
		return pretendedResult;
	}
	public void setPretendedResult(PretendedResult pretendedResult) {
		this.pretendedResult = pretendedResult;
	}

}
