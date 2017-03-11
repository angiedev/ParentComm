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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((schoolId == null) ? 0 : schoolId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Class other = (Class) obj;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (id != other.id)
			return false;
		if (schoolId == null) {
			if (other.schoolId != null)
				return false;
		} else if (!schoolId.equals(other.schoolId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Class [id=" + id + ", schoolId=" + schoolId + ", grade=" + grade + ", teacher=" + teacher + "]";
	} 
	
}
