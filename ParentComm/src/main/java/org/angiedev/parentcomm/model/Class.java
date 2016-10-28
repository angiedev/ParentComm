package org.angiedev.parentcomm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.angiedev.parentcomm.model.Teacher; 

/**
 * Class is a data model representing a child's class in our application
 * @author Angela Gordon
 */

@Entity
@Table(name="Class")
@NamedQueries(
		{ @NamedQuery(name = "Class.findBySchoolId", query = "from Class where school_id=:schoolId")})
public class Class {

	private long id; 
	private String schoolId;
	private String grade; 
	private Teacher teacher;
	
	@Id 
	@GeneratedValue 
	@Column(name="class_id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="school_id")
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Column(name="grade")
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name="teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	} 
	
	
	
	
	
	
}
