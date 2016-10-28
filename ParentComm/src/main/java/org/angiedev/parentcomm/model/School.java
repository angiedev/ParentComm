package org.angiedev.parentcomm.model;


/**
 * School is a data model representing a child's school in our application
 * @author Angela Gordon
 */
public class School {

	protected String id; // ncesId
	protected String name;
	protected String streetAddress;
	protected String city;
	protected String state;
	protected String zip;
	protected SchoolGrade lowGrade;  // lowest grade at school
	protected SchoolGrade highGrade; // highest grade at school
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id =id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public SchoolGrade getLowGrade() {
		return lowGrade;
	}

	public void setLowGrade(SchoolGrade lowGrade) {
		this.lowGrade = lowGrade;
	}

	public SchoolGrade getHighGrade() {
		return highGrade;
	}

	public void setHighGrade(SchoolGrade highGrade) {
		this.highGrade = highGrade;
	}
	
	/**
	 * Calculates the number of grades taught at the school
	 * @return number of grades at school
	 */
	public int getNumGrades() {

		if ( (highGrade == SchoolGrade.UNGRADED) || (lowGrade == SchoolGrade.UNGRADED)) {
			return -1;
		}
		return (highGrade.getValue() - lowGrade.getValue() + 1);
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((highGrade == null) ? 0 : highGrade.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lowGrade == null) ? 0 : lowGrade.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (highGrade == null) {
			if (other.highGrade != null)
				return false;
		} else if (!highGrade.equals(other.highGrade))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (streetAddress == null) {
			if (other.streetAddress != null)
				return false;
		} else if (!streetAddress.equals(other.streetAddress))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", name=" + name + ", streetAddress=" + streetAddress + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", lowGrade=" + lowGrade + ", highGrade=" + highGrade + "]";
	}
	
	
	
}
