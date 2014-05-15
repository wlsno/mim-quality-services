package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;

public class OutputCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Double probability;
	
	public OutputCategory() {
		super();
	}

	public OutputCategory(String name, Double probability){
		this();
		this.setName(name);
		this.setProbability(probability);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Double getProbability() {
		return probability;
	}
	public void setProbability(Double probability) {
		this.probability = probability;
	}

}
