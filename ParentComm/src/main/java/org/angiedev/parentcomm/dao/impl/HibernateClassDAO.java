package org.angiedev.parentcomm.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List; 

import org.angiedev.parentcomm.dao.ClassDAO;
import org.angiedev.parentcomm.model.Class;

@Repository 

public class HibernateClassDAO implements ClassDAO {

	@Autowired 
	private SessionFactory sessionFactory; 	
	
	@SuppressWarnings("unchecked")
	public List<Class> getClassesBySchoolId( String schoolId ) {
		return (List<Class>)sessionFactory.getCurrentSession().getNamedQuery("Class.findBySchoolId").
				setParameter("schoolId", schoolId, StringType.INSTANCE).getResultList();
	}
	
	public void insertClass(Class c) {
		sessionFactory.getCurrentSession().save(c);
	}
	
	
	
}
