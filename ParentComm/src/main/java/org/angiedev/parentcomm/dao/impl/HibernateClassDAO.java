package org.angiedev.parentcomm.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List; 

import org.angiedev.parentcomm.dao.ClassDAO;
import org.angiedev.parentcomm.model.Class;

@Repository 
@Transactional
public class HibernateClassDAO implements ClassDAO {

	@Autowired 
	private SessionFactory sessionFactory; 	
	
	private Session currentSession() {
		Session session;
		try {
		    session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
		    session = sessionFactory.openSession();
		}
		return session;
	}
	
	@SuppressWarnings("unchecked")
	public List<Class> getClassesBySchoolId( String schoolId ) {
		return (List<Class>)currentSession().getNamedQuery("Class.findBySchoolId").
				setParameter("schoolId", schoolId, StringType.INSTANCE).getResultList();
	}
	
	public void insertClass(Class c) {
		currentSession().save(c);
	}
	
	
	
}
