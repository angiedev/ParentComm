package org.angiedev.parentcomm.dao;

import java.util.List;

import org.angiedev.parentcomm.model.Class;

/**
 * ClassDAO is an interface which defines the data access operations 
 * to find, create and update classes.
 * 
 * @author Angela Gordon
 */ 
public interface ClassDAO {
	public List<Class> getClassesBySchoolId( String schoolId ) ;
	public void insertClass(Class c);
}
