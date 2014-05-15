package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;
import java.util.List;

public class InputCaseData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private List<InputParameter> parameters;
	
	public InputCaseData() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<InputParameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<InputParameter> parameters) {
		this.parameters = parameters;
	}
}
