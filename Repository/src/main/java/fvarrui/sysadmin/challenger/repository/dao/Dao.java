package fvarrui.sysadmin.challenger.repository.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Dao {
	
	public static final String REPO = "repo/challenge.odb";
	
	private static EntityManagerFactory emf = null; 
	private static EntityManager em = null;

	public Dao() {
		if (emf == null) {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.url", REPO);
			emf = Persistence.createEntityManagerFactory("challenge", properties);
		}
		if (em == null) {
			em = emf.createEntityManager();
		}
	}

	public static void close() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected void begin() {
		em.getTransaction().begin();
	}
	
	protected void commit() {
		em.getTransaction().commit();
	}

	protected void rollback() {
		em.getTransaction().rollback();
	}

}
