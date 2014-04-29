package pt.up.med.mim.fh.quality.domain.ws.ouput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormAssessmentOutput implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormDetails details;
	private List<FormField> fields;
	
	public FormAssessmentOutput() {
		super();
	}

	public FormDetails getDetails() {
		return details;
	}
	public void setDetails(FormDetails details) {
		this.details = details;
	}

	public List<FormField> getData() {
		return fields;
	}
	public void setData(List<FormField> data) {
		this.fields = data;
	}
	public void addToFields(FormField field) {
		if(this.fields == null)
			this.fields = new ArrayList<FormField>();
		this.fields.add(field);
	}
	
}
