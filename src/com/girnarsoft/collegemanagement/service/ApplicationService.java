
package com.girnarsoft.collegemanagement.service;

import java.lang.annotation.Annotation;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.girnarsoft.collegemanagement.beans.*;
import com.girnarsoft.collegemanagement.customexception.DepartmentNotFoundException;
import com.girnarsoft.collegemanagement.customexception.StudentNotFoundException;
import com.girnarsoft.collegemanagement.dao.DataAccessObject;
import com.girnarsoft.constants.MessageConstant;

/**
 * @author shubham
 * this class is meant to provide the services to the client.
 *
 */
/**
 * @author gspl
 *
 */
@Service("applicationService")
public class ApplicationService implements MainService{

	@Autowired
	private DataAccessObject dataAccessObject;
	public static final Scanner SCANNER = new Scanner(System.in);
	public void setDataAccessObject(DataAccessObject dataAccessObject) {
		this.dataAccessObject = dataAccessObject;
	}
	
	
	/**
	 * @param studentId to Identify Uniquely 
	 * @param newFirstName parameter that is going to update
	 */
	public void updateFirstName(int studentId,String newFirstName) {
		try {
		dataAccessObject.updateFirstName(studentId,newFirstName);
		} catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("Inserted data is too long\nPlease Enter your first name in less than 20 Char");
		}
	}
	
	public void updateLastName(int studentId,String newLastName) {
		try {
			dataAccessObject.updateLastName(studentId,newLastName);	
		} catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("Inserted data is too long\nPlease Enter your last name in less than 20 Char");
		}
	}
	
	public void updateGenderName(int studentId,String newGender) {
		try {
			dataAccessObject.updateGender(studentId,newGender);
		} catch (Exception e) {
			System.out.println("Some problem with gender");
		}
		
	}
	
	public void updateAddress(int studentId,String newAddress) {
		try {
			dataAccessObject.updateAddress(studentId,newAddress);
		} catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("Inserted data is too long\nPlease Enter your Student's Address in less than 250 Char");
		}
		
	}
	
	public void updateDepartment(int studentId,int newDepartmentId) {
		dataAccessObject.updateDepartment(studentId,newDepartmentId);
	}
	
	public void isStudentExist(int studentId) throws StudentNotFoundException {
		int count = dataAccessObject.isStudentExist(studentId);
		if(count <= 0)
		{
			throw new StudentNotFoundException("Could not find student with ID " + studentId);
		}	
	}
	
	public void isDepartmentExist(int newDepartmentId) throws DepartmentNotFoundException {
		int count = dataAccessObject.isDepartmentExist(newDepartmentId);
		if(count <= 0)
		{
			throw new DepartmentNotFoundException("Could not find Department with ID " + newDepartmentId);
		}
	}
	
	/**
	 * @param studentId
	 * @throws StudentNotFoundException If the Student does not exist in the database it will throw an Exception
	 */
	public void isStudentExistInMarks(int studentId) throws StudentNotFoundException {
		int count = dataAccessObject.isStudentExistInMarks(studentId);
		if(count <= 0) {
			throw new StudentNotFoundException("No result set for this student yet, so you can't generate Marksheet");
		}
	}
	
	public int getNewStudentId() {
		int newId = dataAccessObject.getNewStudentId();
		return newId;
	}
	
	/**
	 * @param department Adding A NEW Department in Department
	 */
	public void addDepartment(String department) {
		dataAccessObject.insertDepartment(department);
	}
	
	/**
	 * @param student A new student insert in the database.
	 * AddStudent method will add a new student in the database.
	 */
	public void addStudent(Student student) {
		try {
			dataAccessObject.insertStudent(student);
		}catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException(student.getFirstName());
		}
		
	}
	
	/**
	 * @param studentId
	 * Remove Student WIll remove the student from Student Table and Will also remove from the marks table.
	 */
	public void removeStudent(int studentId) {
		try {
			System.out.println(MessageConstant.DELETE_MENU);
			int choice = validateInteger();
			switch(choice) {
				case 1: 
					break;
				case 2:
					dataAccessObject.deleteStudent(studentId);
					break;
				default:
					System.out.println("Please Enter Only Given Choices");
			}
			
		} catch (Exception e) {
			System.err.println("\nSomething Went Wrong In deleting\n");

		}
		
	}
	
	/**
	 * get all the subject list that a student is registered. 
	 * @param studentId student id for which subject list is needed.
	 * @return
	 */
	public List getSubjectsOfStudent(int studentId) {
		List listofSubjects = dataAccessObject.listOfSubjectByStudentId(studentId);
		return listofSubjects;
	}
	
	
	public List getDepartments() {
		List listOfDepartments = dataAccessObject.listOfDepartments();
		return listOfDepartments;
	}
	
	public int getTotalNumberOfDepartment() {
		int size = dataAccessObject.getDepartmentSize();
		return size;
	}
	
	
	/**
	 * @param studentId for which student, mark need to update
	 * @param subjectIdMap all the subject's Id are in the list
	 * @param subjectMark all The corresponding marks of subject are in this list
	 */
	public void updateAllMarks(int studentId, List<Long> subjectIdMap, List<Integer> subjectMark) {
		for(int indexOfList = 0; indexOfList < subjectIdMap.size(); ++indexOfList)
		{
			int count = dataAccessObject.isStudentSubjectExist(studentId, subjectIdMap.get(indexOfList));
			if(count==0)
			{
				dataAccessObject.insertMark(studentId, subjectIdMap.get(indexOfList), subjectMark.get(indexOfList));
			}
			else
			{
				dataAccessObject.updateSubjectMark(studentId, subjectIdMap.get(indexOfList), subjectMark.get(indexOfList));
			}
		}
		System.out.println("Marks Inserted Successfully");
		
	}
	
	/**
	 * @param studentId for which student mark need to set
	 * @param subjectId for which subject mark need to set
	 * @param newMark mark 
	 * @return
	 */
	public String updateMark(int studentId, long subjectId, int newMark ) {
		if(newMark < 0 || newMark >100)
		{
			return "Sorry, Marks can not be greater than 100 or less than 0 \nThank you !!";
		}
		int count = dataAccessObject.isStudentSubjectExist(studentId, subjectId);
		if(count == 0) {
			dataAccessObject.insertMark(studentId, subjectId, newMark);
		}
		else
		{
			dataAccessObject.updateSubjectMark(studentId, subjectId, newMark);
		}
		return "Marks inserted Successfully";
		
	}
	/**
	 * generate Mark Sheet for a Student.
	 * @param studentId
	 */
	public void generateMarksheet(int studentId) {
		
		List<Map<String, Object>> markSheet = dataAccessObject.generateMarksheet(studentId);
		int countSubject = 0;
		int totalMarks = 0;
		if(markSheet != null && !markSheet.isEmpty())
		{
			System.out.format("%30s%30s%12s","student","Subject","Marks");
			System.out.println("");
			for (Map<String, Object> subject : markSheet) {
				for(Iterator<Map.Entry<String, Object>> iterator = subject.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<String, Object> entry = iterator.next();
					
					
					String key = entry.getKey();
					Object value = entry.getValue();
					if(key.equals("mark"))
					{
						totalMarks += (Integer)value;
						countSubject++;
						//System.out.print(value+"/100\t" );
						System.out.format("%11d",value);
					}
					else
					{
						//System.out.print(value+" \t");
						System.out.format("%30s",value);
					}
					
				}
				System.out.println("");
			}
			System.out.println("The Average of All Subject = " + (double)((double)totalMarks/((double)countSubject)));
			System.out.println("");

		}
		
	}
	
	
	public void addBatchStudents(List<Student> newStudents) {
		
		dataAccessObject.insertBatchStudents(newStudents);
		
	}

	public List<Student> getAllStudents() {
		return dataAccessObject.listStudents();
	}
	
	public void updateBatchMarkByN(int extraMark)
	{
		dataAccessObject.updateBatchMarkByN(extraMark);
	}
	
	private int validateInteger() {
        int retValue=-1;
        boolean flag = true;
        
        while(flag) {
        	try {
           	 retValue = Integer.parseInt(SCANNER.nextLine());
           	 flag = false;
           } catch(NumberFormatException ex)
           {
           	System.err.println("That's not a valid Integer Please Enter Valid Input and Input should be in Integer Range!!\n");
           }
        }
        
        return retValue;
       
        
        
    }
}
