package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class InputCase implements Serializable, IServiceObject {

	private static final long serialVersionUID = 1L;

	private InputCaseData data;
	private InputCaseConfig configuration;

	public InputCase() {
		super();
	}

	public InputCaseData getData() {
		return data;
	}
	public void setData(InputCaseData data) {
		this.data = data;
	}

	public InputCaseConfig getConfiguration() {
		return configuration;
	}
	public void setConfiguration(InputCaseConfig configuration) {
		this.configuration = configuration;
	}
}
