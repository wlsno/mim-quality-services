package pt.up.med.mim.fh.quality.app.dao;

import javax.persistence.Query;

import pt.up.med.mim.fh.quality.base.dao.DAOBase;
import pt.up.med.mim.fh.quality.domain.entities.Form;

public class QualityModuleDAO extends DAOBase {
	//private EntityManagerFactory entityManagerFactory;
	
	public QualityModuleDAO() {
		super("fh-quality-unit");
	}
	
	public Form getForm(String name, String version) {
//		EntityManager em = null;
		try {
//			entityManagerFactory = Persistence.createEntityManagerFactory("fh-quality-unit");
//			em = entityManagerFactory.createEntityManager();
//			
			Query query = createQuery("from Form where bayesianNetworkName = :name and active = :active and version = :version");
			query.setParameter("name", name);
			query.setParameter("active", name);
			query.setParameter("version", version);
			
			return (Form) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
