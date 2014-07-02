package pt.up.med.mim.fh.quality.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pt.up.med.mim.fh.quality.domain.inference.entities.Form;

public class QualityModuleDAO {
	private EntityManagerFactory entityManagerFactory;
	
	public Form getForm(String name, String version) {
		EntityManager em = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("fh-quality-unit");
			em = entityManagerFactory.createEntityManager();
			
			Query query = em.createQuery("from Form where bayesianNetworkName = :name and active = :active and version = :version");
			query.setParameter("name", name);
			query.setParameter("active", name);
			query.setParameter("version", version);
			
			return (Form) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
}
