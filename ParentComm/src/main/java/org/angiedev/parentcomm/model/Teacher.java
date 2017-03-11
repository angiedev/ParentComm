package org.angiedev.parentcomm.model;

/**
 * Teacher is a data model representing a school's teacher in our application
 * @author Angela Gordon
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Teacher")
public class Teacher {

	private long id;
	private String prefix;
	private String firstName;
	private String lastName;
	
	@Id
	@GeneratedValue
	@Column(name="teacher_id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="prefix")
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@Column(name="first_name")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
		Teacher other = (Teacher) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", prefix=" + prefix + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
