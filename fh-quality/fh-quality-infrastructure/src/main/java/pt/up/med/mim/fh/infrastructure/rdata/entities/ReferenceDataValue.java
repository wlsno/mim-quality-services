package pt.up.med.mim.fh.infrastructure.rdata.entities;

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
@Table(name="referencedatavalue")
public class ReferenceDataValue extends DomainObject {
	private String referencedatakey;
	private String referencedatavalue; 
	private ReferenceDataValueType referencedatatype;
	private String description; 
	private ReferenceData referencedata;
	
	public ReferenceDataValue() {
		super();
	}

	@Column(name="referencedatakey", nullable=false)
	public String getReferencedatakey() {
		return referencedatakey;
	}
	public void setReferencedatakey(String referencedatakey) {
		this.referencedatakey = referencedatakey;
	}

	@Column(name="referencedatavalue", nullable=false)
	public String getReferencedatavalue() {
		return referencedatavalue;
	}
	public void setReferencedatavalue(String referencedatavalue) {
		this.referencedatavalue = referencedatavalue;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="referencedatatype", nullable=false)
	public ReferenceDataValueType getReferencedatatype() {
		return referencedatatype;
	}
	public void setReferencedatatype(ReferenceDataValueType referencedatatype) {
		this.referencedatatype = referencedatatype;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="referencedataid", nullable=false, referencedColumnName="id")
	public ReferenceData getReferencedata() {
		return referencedata;
	}
	public void setReferencedata(ReferenceData referencedata) {
		this.referencedata = referencedata;
	}
}
