package pt.up.med.mim.fh.quality.domain.ws.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import pt.up.med.mim.fh.quality.domain.inference.common.InferenceAlgorithm;

public class FormInputData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String formID;
	private List<InputField> fields;
	private Character noEvidenceMarker;
	private InferenceAlgorithm algorithm;

	public FormInputData() {
		super();
	}

	public String getFormID() {
		return formID;
	}
	public void setFormID(String formID) {
		this.formID = formID;
	}

	public Character getNoEvidenceMarker() {
		return noEvidenceMarker;
	}
	public void setNoEvidenceMarker(Character noEvidenceMarker) {
		this.noEvidenceMarker = noEvidenceMarker;
	}

	public InferenceAlgorithm getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(InferenceAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public List<InputField> getFields() {
		return fields;
	}
	public void setFields(List<InputField> fields) {
		this.fields = fields;
	}

	public void addField(final InputField field) {
		if (null == this.fields)
			this.fields = new ArrayList<InputField>();

		Boolean exists = CollectionUtils.exists(this.fields, new Predicate() {

			public boolean evaluate(Object arg0) {
				return ((InputField) arg0).getPath().equalsIgnoreCase(field.getPath())
					|| ((InputField) arg0).getAlias().equalsIgnoreCase(field.getAlias());
			}
		});

		if (!exists)
			this.fields.add(field);
	}

}
