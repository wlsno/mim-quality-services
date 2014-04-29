package pt.up.med.mim.fh.quality.domain.inference.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

//import pt.up.med.mim.fh.quality.domain.inference.common.QueryType;

public class ParameterBean {

	private String openEHRpath;
	private String alias;
	private Boolean hasEvidence = Boolean.FALSE;
	private List<ParameterInstanceBean> instances;

//	private QueryType type;
	
	public ParameterBean() {
	}

	public ParameterBean(String alias, String openEHRpath) {
		this.setAlias(alias);
		this.setOpenEHRpath(openEHRpath);
	}

	public ParameterBean(String alias, String openEHRpath, List<ParameterInstanceBean> instances) {
		this.setAlias(alias);
		this.setOpenEHRpath(openEHRpath);
		this.setInstances(instances);
	}

	public String getOpenEHRpath() {
		return openEHRpath;
	}

	public void setOpenEHRpath(String openEHRpath) {
		this.openEHRpath = openEHRpath;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Boolean getHasEvidence() {
		return hasEvidence;
	}

	public void setHasEvidence(Boolean hasEvidence) {
		this.hasEvidence = hasEvidence;
	}

	public List<ParameterInstanceBean> getInstances() {
		return instances;
	}

	public void setInstances(List<ParameterInstanceBean> instances) {
		this.instances = instances;
	}

	/**
	 * Adds the instance to the list.
	 * 
	 * @param instance
	 *            - The parameter instance to add
	 * @return true if the parameter instance was added or false if the
	 *         parameter instance already exists
	 */
	public boolean addToInstances(ParameterInstanceBean instance) {
		if (CollectionUtils.isEmpty(getInstances()))
			this.instances = new ArrayList<ParameterInstanceBean>();

		if (getInstances().contains(instance))
			return false;

		getInstances().add(instance);
		return true;
	}

	public void setEvidenceOnParameterInstance(String instanceName) {
		for (ParameterInstanceBean instance : this.instances) {
			if (instance.getInstanceName().equalsIgnoreCase(instanceName)) {
				this.setHasEvidence(Boolean.TRUE);
				instance.setIsEvidence(Boolean.TRUE);
			}
		}
	}
	
	public ParameterInstanceBean getParameterEvidence(){
		if (this.hasEvidence && this.instances != null)
			return (ParameterInstanceBean) CollectionUtils.find(this.instances, new Predicate() {
				public boolean evaluate(Object arg0) {
					return ((ParameterInstanceBean)arg0).getIsEvidence();
				}
			});
		
		return null;
	}
	
	/**
	 * Gets the Most Probable Category infered fr this parameter
	 * @return The Most Porbable Category for this parameter or null if no category is found
	 */
	public ParameterInstanceBean getMPC() {
		if(CollectionUtils.isEmpty(this.instances))
			return null;
		
		Collections.sort(this.instances);
		
		return this.instances.get(0);
	}

}
