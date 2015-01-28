package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class InputCaseDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private GenericCode formIdentifier;
	
	public InputCaseDetail() {
	}

	public GenericCode getFormIdentifier() {
		return formIdentifier;
	}

	public void setFormIdentifier(GenericCode formIdentifier) {
		this.formIdentifier = formIdentifier;
	}
	
}
