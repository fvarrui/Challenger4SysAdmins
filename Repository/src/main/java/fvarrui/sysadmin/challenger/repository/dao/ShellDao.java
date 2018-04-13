package fvarrui.sysadmin.challenger.repository.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fvarrui.sysadmin.challenger.repository.entities.ShellEntity;

public class ShellDao extends Dao {
	
	public ShellEntity findById(Long id) {
		return getEntityManager().find(ShellEntity.class, id);
	}
	
	public ShellEntity findByName(String name) {
		TypedQuery<ShellEntity> query = getEntityManager().createQuery("select from ShellEntity s where s.name = :name", ShellEntity.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}
	
	public List<ShellEntity> getAll() {
		return getEntityManager().createQuery("select from ShellEntity", ShellEntity.class).getResultList();
	}

	public void save(ShellEntity entity) {
		try {
			begin();
			getEntityManager().persist(entity);
			commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			rollback();
		}
	}
	
	public void remove(ShellEntity entity) {
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
