package org.angiedev.parentcomm.model;

/**
 * School is a data model representing a school in our application
 * @author Angela Gordon
 */
public class School {

	protected String name;
	protected long id;
	protected String address;
	protected String lowGrade;  // lowest grade at school
	protected String highGrade; // highest grade at school
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id =id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLowGrade() {
		return lowGrade;
	}

	public void setLowGrade(String lowGrade) {
		this.lowGrade = lowGrade;
	}

	public String getHighGrade() {
		return highGrade;
	}

	public void setHighGrade(String highGrade) {
		this.highGrade = highGrade;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((highGrade == null) ? 0 : highGrade.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lowGrade == null) ? 0 : lowGrade.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		School other = (School) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (highGrade == null) {
			if (other.highGrade != null)
				return false;
		} else if (!highGrade.equals(other.highGrade))
			return false;
		if (id != other.id)
			return false;
		if (lowGrade == null) {
			if (other.lowGrade != null)
				return false;
		} else if (!lowGrade.equals(other.lowGrade))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "School [name=" + name + ", id=" + id + ", address=" + address + ", lowGrade=" + lowGrade
				+ ", highGrade=" + highGrade + "]";
	}
	
}
