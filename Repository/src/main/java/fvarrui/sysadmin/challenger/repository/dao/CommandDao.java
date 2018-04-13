package fvarrui.sysadmin.challenger.repository.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fvarrui.sysadmin.challenger.repository.entities.CommandEntity;

public class CommandDao extends Dao {
	
	public CommandEntity findById(Long id) {
		return getEntityManager().find(CommandEntity.class, id);
	}
	
	public CommandEntity findByName(String name) {
		TypedQuery<CommandEntity> query = getEntityManager().createQuery("select from CommandEntity c where c.name = :name", CommandEntity.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}
	
	public List<CommandEntity> getAll() {
		return getEntityManager().createQuery("select from CommandEntity", CommandEntity.class).getResultList();
	}

	public void save(CommandEntity entity) {
		try {
			begin();
			getEntityManager().persist(entity);
			commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			rollback();
		}
	}
	
	public void remove(CommandEntity entity) {
		try {
			begin();
			getEntityManager().remove(entity);
			commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			rollback();
		}
	}
	
}
