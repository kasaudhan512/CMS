package com.girnarsoft.collegemanagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.girnarsoft.collegemanagement.beans.Student;

public class StudentMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		Student student = new Student();
		student.setStudentId(resultSet.getInt("std_id"));
		student.setFirstName(resultSet.getString("first_name"));
		student.setLastName(resultSet.getString("last_name"));
		student.setGender(resultSet.getString("gender"));
		student.setAddress(resultSet.getString("address"));
		student.setDepartmentId(resultSet.getInt("dep_id"));
		return student;
	}

}
