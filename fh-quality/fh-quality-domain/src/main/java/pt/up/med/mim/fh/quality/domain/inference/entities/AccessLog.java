package pt.up.med.mim.fh.quality.domain.inference.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pt.up.med.mim.fh.quality.base.entities.DomainObject;

@Entity
@Table(name = "accesslog")
public class AccessLog extends DomainObject /* implements Serializable */{

	// private static final long serialVersionUID = 1L;

	private String sessionID;
	private Date accessDate;
	private String bayesianNetworkName;
	private ActionResult result;
	private Set<AccessLogDetail> accessLogDetails;
	
	public AccessLog() {
		super();
	}

	@Column(name = "sessionid", nullable = false)
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	@Column(name = "accessdate", columnDefinition = "DATETIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	@Column(name = "bayesiannetworkname")
	public String getBayesianNetworkName() {
		return bayesianNetworkName;
	}

	public void setBayesianNetworkName(String bayesianNetworkName) {
		this.bayesianNetworkName = bayesianNetworkName;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "result")
	public ActionResult getResult() {
		return result;
	}

	public void setResult(ActionResult result) {
		this.result = result;
	}

	@OneToMany(mappedBy="log", fetch=FetchType.LAZY)
	public Set<AccessLogDetail> getAccessLogDetails() {
		return accessLogDetails;
	}
	public void setAccessLogDetails(Set<AccessLogDetail> accessLogDetails) {
		this.accessLogDetails = accessLogDetails;
	}
	
}
