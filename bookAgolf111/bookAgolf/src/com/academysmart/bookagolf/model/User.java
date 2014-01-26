package com.academysmart.bookagolf.model;

/**
 * Users
 * 
 */
public class User {

	private Long id;
	private String name;
	private String surname;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public String getFullName() {
		return name + " " + surname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname
				+ "]";
	}

}
