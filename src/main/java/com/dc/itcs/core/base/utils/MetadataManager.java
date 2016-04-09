package com.dc.itcs.core.base.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class MetadataManager {
	@PersistenceContext
	private EntityManager em;
	
	public<T> T getEntity(Class<T> clazz, Long id){
		return em.find(clazz, id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getEntity(String entityClass, Long entityId) {
		Class clazz;
		try {
			clazz = Class.forName(entityClass);
			return getEntity(clazz,entityId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void saveEntity(Object obj){
		em.merge(obj);
	}
}
