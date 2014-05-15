package pt.up.med.mim.fh.quality.service.domain.entities;

import java.io.Serializable;
import java.util.List;

public class OutputParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private List<OutputCategory> results;
	private OutputCategory mostProbableCategory;
	
	public OutputParameter() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<OutputCategory> getResults() {
		return results;
	}
	public void setResults(List<OutputCategory> results) {
		this.results = results;
	}

	public OutputCategory getMostProbableCategory() {
		return mostProbableCategory;
	}
	public void setMostProbableCategory(OutputCategory mostProbableCategory) {
		this.mostProbableCategory = mostProbableCategory;
	}

}
