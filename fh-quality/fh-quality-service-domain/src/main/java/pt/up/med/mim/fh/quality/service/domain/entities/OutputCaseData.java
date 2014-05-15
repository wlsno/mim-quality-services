package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;
import java.util.List;

public class OutputCaseData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private List<OutputParameter> parameters;

	public OutputCaseData() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<OutputParameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<OutputParameter> parameters) {
		this.parameters = parameters;
	}

}
