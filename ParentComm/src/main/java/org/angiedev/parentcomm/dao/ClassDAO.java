package org.angiedev.parentcomm.dao;

import java.util.List;

import org.angiedev.parentcomm.model.Class;

public interface ClassDAO {
	public List<Class> getClassesBySchoolId( String schoolId ) ;
	public void insertClass(Class c);
}
