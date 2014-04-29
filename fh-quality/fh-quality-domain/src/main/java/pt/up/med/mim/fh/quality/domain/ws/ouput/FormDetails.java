package pt.up.med.mim.fh.quality.domain.ws.ouput;

import java.io.Serializable;

public class FormDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	public FormDetails() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
