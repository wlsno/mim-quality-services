package pt.up.med.mim.fh.quality.listeners;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PersistenceListener implements ServletContextListener {

	private EntityManagerFactory entityManagerFactory;

	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext context = sce.getServletContext();
			entityManagerFactory = Persistence.createEntityManagerFactory("fh-quality-unit");
		} catch (Throwable ex) {
			ex.printStackTrace();
			System.err.println("Failed to create entityManagerFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		entityManagerFactory.close();
	}
}