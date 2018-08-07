package com.girnarsoft.collegemanagement.beans;

public class Student {

	private int studentId;
	private String firstName;
	private String lastName;
	private String gender;
	private String address;
	private int departmentId;
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public String toString() {
		String print = "";
		print += this.getFirstName() + "\t" + this.getLastName() + "\t" + this.getGender() + "\t" + this.getAddress();
		return print;
	}
	
	
}
