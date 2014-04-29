package pt.up.med.mim.fh.quality.domain.ws.input;

import java.io.Serializable;

//import pt.up.med.mim.fh.quality.domain.inference.common.QueryType;

public class InputField implements Serializable {

	private static final long serialVersionUID = 1L;

	private String alias;
	private String path;
	private Boolean hasEvidence;
	private String value;
	//private QueryType resultType;
	
	public InputField() {
		super();
	}
	
	public InputField(String path, String value){
		this();
		this.setPath(path);
		this.setValue(value);
	}
	
	public InputField(String alias, String path, String value){
		this();
		this.setAlias(alias);
		this.setPath(path);
		this.setValue(value);
	}
	
	public InputField(String alias, String path, String value, Boolean hasEvidence){
		this();
		this.setAlias(alias);
		this.setPath(path);
		this.setValue(value);
		this.setHasEvidence(hasEvidence);
		//this.setResultType(type);
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

	public Boolean getHasEvidence() {
		return hasEvidence;
	}
	public void setHasEvidence(Boolean hasEvidence) {
		this.hasEvidence = hasEvidence;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

//	public QueryType getResultType() {
//		return resultType;
//	}
//	public void setResultType(QueryType resultType) {
//		this.resultType = resultType;
//	}
}
