package pt.up.med.mim.fh.quality.domain.ws.ouput;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class FormField implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fieldId;
	private String alias;
	private String path;
	private List<FieldInstance> instances;
	
	public FormField() {
		super();
	}

	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public List<FieldInstance> getInstances() {
		return instances;
	}
	public void setInstances(List<FieldInstance> values) {
		this.instances = values;
	}
	
	public FieldInstance getMPC() {
		if(CollectionUtils.isEmpty(this.instances))
			return null;
		
		Collections.sort(this.instances);
		
		return this.instances.get(0);
	}
}
