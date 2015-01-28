package pt.up.med.mim.fh.quality.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import pt.up.med.mim.fh.quality.base.entities.DomainObject;

@Entity
@Table(name="form")
public class Form extends DomainObject {
	
	private String description;
	private String bayesianNetworkName;
	private String bayesianNetworkFileName;
	private String version;
	private Boolean active;
	
	public Form() {
		super();
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="bayesiannetworkname", nullable=false)
	public String getBayesianNetworkName() {
		return bayesianNetworkName;
	}
	public void setBayesianNetworkName(String bayesianNetworkName) {
		this.bayesianNetworkName = bayesianNetworkName;
	}
	
	@Column(name="bayesiannetworkfilename", nullable=false)
	public String getBayesianNetworkFileName() {
		return bayesianNetworkFileName;
	}
	public void setBayesianNetworkFileName(String bayesianNetworkFileName) {
		this.bayesianNetworkFileName = bayesianNetworkFileName;
	}

	@Column(name="version", nullable=false)
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name="active", nullable=false)
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
