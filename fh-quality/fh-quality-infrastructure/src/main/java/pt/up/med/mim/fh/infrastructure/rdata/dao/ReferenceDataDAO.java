package pt.up.med.mim.fh.infrastructure.rdata.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pt.up.med.mim.fh.infrastructure.rdata.entities.ReferenceDataType;
import pt.up.med.mim.fh.infrastructure.rdata.entities.ReferenceDataValue;

public class ReferenceDataDAO {

	private EntityManagerFactory entityManagerFactory;

	public String getValue(ReferenceDataType type, String key) {
		EntityManager em = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("fh-quality-unit");
			em = entityManagerFactory.createEntityManager();
			Query query = em.createQuery("from ReferenceDataValue where referencedata.type = :type and referencedatakey = :key");
			query.setParameter("type", type);
			query.setParameter("key", key);
			ReferenceDataValue value = (ReferenceDataValue) query.getSingleResult();
			
			return value.getReferencedatavalue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
	
	public Integer getInteger(ReferenceDataType type, String key) {
		return Integer.parseInt(getValue(type, key));
	}
	
	public Boolean getBoolean(ReferenceDataType type, String key) {
		return Boolean.parseBoolean(getValue(type, key)) || getValue(type, key).equals("1");
	}
}
