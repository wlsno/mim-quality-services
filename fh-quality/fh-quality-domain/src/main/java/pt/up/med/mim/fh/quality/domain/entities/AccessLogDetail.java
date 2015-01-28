package pt.up.med.mim.fh.quality.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pt.up.med.mim.fh.quality.base.entities.DomainObject;

@Entity
@Table(name="accesslogdetail")
public class AccessLogDetail extends DomainObject /*implements Serializable */  {

//	private static final long serialVersionUID = 1L;
	
	private AccessLog log;
	private AccessLogDetailType detailType;
	private String detailValue;
	
	public AccessLogDetail() {
		super();
	}
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=AccessLog.class)
	@JoinColumn(name="logid", nullable=false)
	public AccessLog getLog() {
		return log;
	}
	public void setLog(AccessLog log) {
		this.log = log;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="detailtype", nullable=false)
	public AccessLogDetailType getDetailType() {
		return detailType;
	}
	public void setDetailType(AccessLogDetailType detailType) {
		this.detailType = detailType;
	}

	@Column(name="detailvalue", columnDefinition="varchar(max)")
	public String getDetailValue() {
		return detailValue;
	}
	public void setDetailValue(String detailValue) {
		this.detailValue = detailValue;
	}

}
