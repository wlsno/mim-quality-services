package pt.up.med.mim.fh.quality.domain.ws.ouput;

import java.io.Serializable;
import java.util.Comparator;

public class FieldInstance implements Serializable, Comparator<FieldInstance>, Comparable<FieldInstance> {

	private static final long serialVersionUID = 1L;

	private String instance;
	private Double result;
	
	public FieldInstance() {
		super();
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public int compareTo(FieldInstance o) {
		if(o == null || o.getResult() == null)
			return -1;
		return o.getResult().compareTo(this.getResult());
	}

	public int compare(FieldInstance o1, FieldInstance o2) {
		if(o1 == null || o1.getResult() == null)
			return -1;
		
		if(o2 == null || o2.getResult() == null)
			return -1;
		
		return o2.getResult().compareTo(o1.getResult());
	}

	
}
