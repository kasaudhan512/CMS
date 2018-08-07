package com.girnarsoft.collegemanagement.dao;

import java.lang.reflect.Executable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.girnarsoft.collegemanagement.beans.Student;

@Repository
public class DataAccessObject implements Dao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int isStudentExist(int std_id) {
		String sql = "select count(*) from student where std_id = ?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { std_id }, Integer.class);
		return count;
	}
	
	public int isDepartmentExist(int dep_id) {
		String sql = "select count(*) from department where dep_id = ?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { dep_id }, Integer.class);
		return count;
	}
	
	public int isStudentExistInMarks(int std_id) {
		String sql = "select count(*) from marks where std_id = ?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { std_id }, Integer.class);
		return count;
	}
	
	public int isStudentSubjectExist(int studentId, long subjectId) {
		String sql = "select count(*) from marks where std_id = ? and sub_id = ?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { studentId, subjectId }, Integer.class);
		return count;
	}
	
	public void updateFirstName(int studentId,String newFirstName) {
		String sql = "update student set first_name = ? where std_id = ?";
		jdbcTemplate.update(sql, newFirstName, studentId);
		System.out.println("Updated Record with ID = " + studentId );
	}
	
	public void updateLastName(int studentId,String newLastName) {
		String sql = "update student set last_name = ? where std_id = ?";
		jdbcTemplate.update(sql, newLastName, studentId);
		System.out.println("Updated Record with ID = " + studentId );
	}
	
	public void updateGender(int studentId,String newGender) {
		String sql = "update student set gender = ? where std_id = ?";
		jdbcTemplate.update(sql, newGender, studentId);
		System.out.println("Updated Record with ID = " + studentId );
	}
	
	public void updateAddress(int studentId,String newAddress) {
		String sql = "update student set address = ? where std_id = ?";
		jdbcTemplate.update(sql, newAddress, studentId);
		System.out.println("Updated Record with ID = " + studentId );
	}
	
	public void updateDepartment(int studentId,int newDepartmentId) {
		String sql = "update student set dep_id = ? where std_id = ?";
		jdbcTemplate.update(sql, newDepartmentId, studentId);
		System.out.println("Updated Record with ID = " + studentId );
	}
	
	public void insertDepartment(String department) {
		String sql = "INSERT INTO department(dep_name) VALUES(?)";
		int update = jdbcTemplate.update(sql, department);
		if(update>0)
		{
			System.out.println("Inserted Successfullly");
		}
	}
	
	public void insertMark(int studentId, long subjectId, int newMark) {
		String sql = "INSERT INTO marks(std_id, sub_id, mark) VALUES(?,?,?)";
		int update = jdbcTemplate.update(sql, studentId, subjectId, newMark );
	}
	
	public void insertStudent(Student student)  {
		String sql = "INSERT INTO student(first_name, last_name, gender, address, dep_id, created_at, updated_at) VALUES(?,?,?,?,?,NOW(),NOW())";
		int update = jdbcTemplate.update(sql, new Object[] {student.getFirstName(),student.getLastName(),student.getGender(),student.getAddress(),student.getDepartmentId()});
		if(update>0)
		{
			System.out.println("Inserted Successfullly");
		}
	}
	
	public void deleteStudent(int studentId) {
		
		String sql = "delete from student where std_id = ?";
		int row = jdbcTemplate.update(sql, studentId);
		System.out.println("Deleted Successfullly");	
		
		
	}
	
	public int getNewStudentId() {
		String sql = "select MAX(std_id) from student";
		int newStudentId = jdbcTemplate.queryForObject(sql,Integer.class);
		return newStudentId;
				
	}
	
	public List listOfSubjectByStudentId(int studentId) {
		String sql = "select sub_id,sub_name from subject where dep_id in(select dep_id from student where std_id = ? )";
		List listOfSubjects = jdbcTemplate.queryForList(sql,studentId);
		return listOfSubjects;
	}
	
	public List listOfDepartments() {
		String sql = "select * from department";
		List listOfDepartments = jdbcTemplate.queryForList(sql);
		return listOfDepartments;
	}
	
	public int getDepartmentSize() {
		String sql = "select count(*) from department";
		int size = jdbcTemplate.queryForObject(sql, Integer.class);
		return size;
	}
	
	public void updateSubjectMark(int studentId, long subjectId, int newMark) {
		String sql = "update marks set mark = ? where std_id = ? and sub_id = ?";
		jdbcTemplate.update(sql, newMark, studentId, subjectId);
	}
	
	public List generateMarksheet(int studentId) {
		String sql = "select student.first_name, subject.sub_name, marks.mark from marks join student on student.std_id = marks.std_id join subject on subject.sub_id=marks.sub_id where marks.std_id=? ";
		List markSheet = jdbcTemplate.queryForList(sql,studentId);
		return markSheet;
	}
	
	public List<Student> listStudents() {
	      String SQL = "select std_id,first_name, last_name, gender, address, dep_id from student";
	      List <Student> students = jdbcTemplate.query(SQL, new StudentMapper());
	      return students;
	   }
	
	public void updateBatchMarkByN(int extraMark)
	{
		String SQL = "update marks set mark = mark + " + extraMark;
		jdbcTemplate.batchUpdate(SQL);
		
	}
	
	public void insertBatchStudents(List<Student> newStudents) {
		String sql = "INSERT INTO student(first_name, last_name, gender, address, dep_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			
			@Override
			public int getBatchSize() {
				return newStudents.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Student student = newStudents.get(i);
				ps.setString(1, student.getFirstName());
				ps.setString(2, student.getLastName());
				ps.setString(3, student.getGender());
				ps.setString(4, student.getAddress());
				ps.setInt(5, student.getDepartmentId());
				
			}
		  });
		System.out.println("All data inserted successfully");
		/**
		 * second method to use Batch update
		 */
		/*List<Object[]> inputList = new ArrayList<Object[]>();
        for(Student student: newStudents){
            Object[] tmp = {student.getFirstName(), student.getLastName(), student.getGender(), student.getAddress(), student.getDepartmentId()};
            inputList.add(tmp);
        }
        jdbcTemplate.batchUpdate(sql, inputList);*/
	}
}
