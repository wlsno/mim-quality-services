package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class InputField implements Serializable {

	private static final long serialVersionUID = 1L;

	private GenericCode field;
	private String value;

	private Boolean isEvidence;

	public InputField() {
		super();
	}

	public InputField(String id, String description, String value, Boolean isEvidence) {
		this.setField(new GenericCode(id, description));
		this.setValue(value);
		this.setIsEvidence(isEvidence);
	}

	public GenericCode getField() {
		return field;
	}

	public void setField(GenericCode field) {
		this.field = field;
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

}
