package pt.up.med.mim.fh.infrastructure.rdata.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pt.up.med.mim.fh.quality.base.entities.DomainObject;

@Entity
@Table(name="referencedata")
public class ReferenceData extends DomainObject {
	private ReferenceDataType type;
	private String description;
	Set<ReferenceDataValue> referenceDataValues = null;
	
	public ReferenceData() {
		super();
	}

	@Enumerated(EnumType.STRING)
	@Column(name="type", nullable=false)
	public ReferenceDataType getType() {
		return type;
	}
	public void setType(ReferenceDataType type) {
		this.type = type;
	}

	@Column(name="description", nullable=false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy="referencedata", fetch=FetchType.LAZY, targetEntity=ReferenceDataValue.class)
	public Set<ReferenceDataValue> getReferenceDataValues() {
		return referenceDataValues;
	}
	public void setReferenceDataValues(Set<ReferenceDataValue> referenceDataValues) {
		this.referenceDataValues = referenceDataValues;
	}
}
