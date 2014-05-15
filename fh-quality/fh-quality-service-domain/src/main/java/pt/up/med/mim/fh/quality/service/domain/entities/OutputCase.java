package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class OutputCase implements Serializable, IServiceObject {

	private static final long serialVersionUID = 1L;

	private OutputCaseDetails details;
	private OutputCaseData data;
	
	public OutputCase() {
		super();
	}

	public OutputCaseDetails getDetails() {
		return details;
	}
	public void setDetails(OutputCaseDetails details) {
		this.details = details;
	}

	public OutputCaseData getData() {
		return data;
	}
	public void setData(OutputCaseData data) {
		this.data = data;
	}

}
