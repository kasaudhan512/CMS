/**
 * This class is Main menu for The client.
 * @author shubham
 */
package com.girnarsoft.collegemanagement.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import com.girnarsoft.collegemanagement.beans.Student;
import com.girnarsoft.collegemanagement.customexception.DepartmentNotFoundException;
import com.girnarsoft.collegemanagement.customexception.StudentNotFoundException;
import com.girnarsoft.collegemanagement.service.ApplicationService;
import com.girnarsoft.constants.MessageConstant;

public class Client {

	public static final Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) {
		
		
		/**
		 * ApplicationContext used to get different objects.
		 */
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ApplicationService applicationService = (ApplicationService) context.getBean("applicationService");

		boolean isTrue = true;
		System.out.println("Welcome to College Management System");
		while(isTrue)
		{
			int userChoice = getInputFromUser(MessageConstant.OPTION_CHOOSER_TEXT + MessageConstant.MENU_OPTION);
			
			switch(userChoice) {
				case 1:
					
					editStudentProfile(applicationService);
					break;
				case 2:
					
					newAddmission(applicationService, context);
					break;
				case 3:
					
					removeStudent(applicationService);
					break;
					
				case 4:
					
					setEditExamResult(applicationService);
					break;
				case 5:
					
					generateMarkSheet(applicationService);	
					break;
				case 6:
					
					someMoreFeature(applicationService,context);
					break;
				case 7:
					
					isTrue = false;
					break;
				default:
					System.out.println("Please choose only given option");
			}
		}
		context.close();
	}
	
	
	public static void editStudentProfile(ApplicationService applicationService)
	{
		boolean correctEditProfile = true;
		while(correctEditProfile)
		{
			int profileOption = getInputFromUser(MessageConstant.OPTION_CHOOSER_TEXT + MessageConstant.UPDATE_OPTION );
			switch(profileOption) {
				case 1: 
					
					updateFirstName(applicationService);
					correctEditProfile = false;
					break;
					
				case 2:
					
					updateLastName(applicationService);
					correctEditProfile = false;
					break;
				case 3:
					
					updateGender(applicationService);
					correctEditProfile = false;
					break;
				case 4:
					
					updateAddress(applicationService);
					correctEditProfile = false;
					break;
					
				case 5:
					
					updateDepartment(applicationService);
					correctEditProfile = false;
					break;
					
				case 6: 
					correctEditProfile = false;
					break;
				default:
					System.out.println("Please Choose Only Given Option :");
			
			}
					
		}
	}
	
	
	/**
	 * Update department of a student
	 * @param applicationService
	 */
	private static void updateDepartment(ApplicationService applicationService) {
		boolean correctEnter = true;
		while(correctEnter)
		{
			try {
				
				int studentId = getInputFromUser("Enter Student's Id  OR Press - 0 to Go Back");
				if(studentId == 0)break;
				applicationService.isStudentExist(studentId);
				while(true)
				{
					try {
						int newDepartmentId = getInputFromUser("Enter New Department_id OR Press - 0 to Go Back");
						if(newDepartmentId == 0)break;
						applicationService.isDepartmentExist(newDepartmentId);
						applicationService.updateDepartment(studentId, newDepartmentId);
						System.out.println("you have successfully Updates Address");
						correctEnter = false;
						break;
					} catch (DepartmentNotFoundException ex) {
						printError(ex);
					} 
				}
				
			} catch(StudentNotFoundException ex) {
				printError(ex);
			} 
		}
	}
	
	
	
	/**
	 * Update Address of a student
	 * @param applicationService
	 */
	private static void updateAddress(ApplicationService applicationService) {
	
		while(true)
		{
			try {
				int studentId = getInputFromUser("Enter Student's Id  OR Press - 0 to Go Back");
				if(studentId == 0)break;
				applicationService.isStudentExist(studentId);
				System.out.println("Enter New Address");
				String newAddress = SCANNER.nextLine();
				applicationService.updateAddress(studentId,newAddress);
				System.out.println("you have successfully Updates Address");
				break;
				
			} catch(StudentNotFoundException ex) {
				printError(ex);
			} catch (DataIntegrityViolationException ex) {
				printError(ex);
			}
			
		}
	}
	
	
	/**
	 * Update gender of a student
	 * @param applicationService
	 */
	private static void updateGender(ApplicationService applicationService) {
		
		while(true)
		{
			try {
				int studentId = getInputFromUser("Enter Student's Id OR Press - 0 to Go Back");
				if(studentId == 0)break;
				applicationService.isStudentExist(studentId);
				System.out.println("choose New gender");
				String newGender = getInputGender();
				applicationService.updateGenderName(studentId, newGender);
				System.out.println("you have successfully Updated Gender");
				break;
				
			} catch(StudentNotFoundException ex) {
				printError(ex);
			}
		}
	}
	
	
	/**
	 * Update last name of a student
	 * @param applicationService
	 */
	private static void updateLastName(ApplicationService applicationService) {
		
		while(true)
		{
			try {
				int studentId = getInputFromUser("Enter Student's Id OR Press - 0 to Go Back");
				if(studentId == 0)break;
				applicationService.isStudentExist(studentId);
				System.out.println("Enter New Last Name");
				String newLastName = takeStringChoice();
				applicationService.updateLastName(studentId,newLastName);
				System.out.println("you have successfully Updates Last Name");
				break;
				
			} catch(StudentNotFoundException ex) {
				printError(ex);
			} catch (DataIntegrityViolationException ex) {
				printError(ex);
			}
			
		}
	}
	
	
	
	/**
	 * update first name of a student
	 * @param applicationService
	 */
	private static void updateFirstName(ApplicationService applicationService) {
		while(true)
		{
			try {
				int studentId = getInputFromUser("Enter Student's Id OR Press - 0 to Go Back");
				if(studentId == 0)break;
				applicationService.isStudentExist(studentId);
				System.out.println("Enter New First Name");
				String newFirstName = takeStringChoice();
				applicationService.updateFirstName(studentId,newFirstName);
				System.out.println("you have successfully Updates First Name");
				break;
				
			} catch(StudentNotFoundException ex) {
				printError(ex);
			} catch (DataIntegrityViolationException ex) {
				printError(ex);
			} 
			
		}
		
	}
	
	

	/**
	 * Generate Marksheet of a Student.
	 * @param applicationService
	 */
	private static void generateMarkSheet(ApplicationService applicationService) {
		while(true) {
			int studentId = getInputFromUser("Please Enter Student's Id whose MarksSheet you want to Generate\nOR Press - 0 to Go Back");
			if(studentId == 0)break;
			try {
				applicationService.isStudentExist(studentId);
				applicationService.isStudentExistInMarks(studentId);
				applicationService.generateMarksheet(studentId);
				break;
				
			} catch(StudentNotFoundException ex) {
				printError(ex);
			}
		}
	}
	
	
	
	/**
	 * Edit Exam Result of a student
	 * @param applicationService
	 */
	private static void setEditExamResult(ApplicationService applicationService) {
		boolean correctStudent = true;
		while(correctStudent) {
			int studentId = getInputFromUser("Please Enter Student's Id whose Marks you want to edit\nOR Press - 0 To Go Back.");
			if(studentId == 0)break;
			try {
				applicationService.isStudentExist(studentId);
				List<Map<String, Object>> listOfSubects = applicationService.getSubjectsOfStudent(studentId);
				editMark(applicationService,listOfSubects,studentId);
				break;
			}catch(StudentNotFoundException ex) {
				printError(ex);
			}
		}
	}
	
	
	
	/**
	 * Remove Student from the database
	 * @param applicationService
	 */
	private static void removeStudent(ApplicationService applicationService) {
		while(true)
		{
			int studentId = getInputFromUser("Please Enter Student's Id whom You want to Remove\nOR Press - 0 to exit");
			if(studentId == 0)break;
			try {
				applicationService.isStudentExist(studentId);
				applicationService.removeStudent(studentId);
				break;
				
			} catch(StudentNotFoundException ex) {
				printError(ex);
				System.out.println("please enter correct Student Id");
			} 
		}
	}
	
	
	
	/**
	 * This Method is actually adding new student in the database
	 * @param applicationService
	 * @param context
	 */
	private static void newAddmission(ApplicationService applicationService ,AbstractApplicationContext context) {
		String firstName,lastName, address, departmentName, gender="M";
		int departmentId=0;
		
		System.out.println("Please Fill All the details of a student\nStudent's First Name");
		firstName = takeStringChoice();
		System.out.println("Student's Last Name");
		lastName = takeStringChoice();
		gender = getInputGender();
		System.out.println("Student's Address");
		address = SCANNER.nextLine();
		departmentId = getInputDepartmentId(applicationService);
		Student student = (Student)context.getBean("student");
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setGender(gender);
		student.setAddress(address);
		student.setDepartmentId(departmentId);
		try {
			applicationService.addStudent(student);
			System.out.println("New Student's Auto Generated Id is - " + applicationService.getNewStudentId() + "\nPlease Remember it for future Work");
		} catch (DataIntegrityViolationException ex) {
			printError(ex);
			System.err.println(MessageConstant.INSERTION_RULE);
		}
		
	}
	
	/**
	 * 
	 * @param applicationService
	 * @param listOfSubects
	 * @param studentId
	 */
	private static void editMark(ApplicationService applicationService, List<Map<String, Object>> listOfSubects,int studentId) {
		
		List<Long> subjectIdMap = new ArrayList<>();
		List<Object> subjectNameMap = new ArrayList<>();
		List<Integer> subjectMark = new ArrayList<>();
		while(true) {
			subjectIdMap.clear();
			subjectNameMap.clear();
			if(listOfSubects != null && !listOfSubects.isEmpty())
			{
				for (Map<String, Object> subject : listOfSubects) {
					for(Iterator<Map.Entry<String, Object>> iterator = subject.entrySet().iterator(); iterator.hasNext();) {
						Map.Entry<String, Object> entry = iterator.next();
						String key = entry.getKey();
						Object value = entry.getValue();
				 		if(key.equals("sub_id"))
						{
							subjectIdMap.add((Long) value);
						}
						else
						{
							System.out.println("Enter mark for the "+ value + " subject\nOR to Skip Press -1\nPress -2 if You are done\n");
							int mark = takeChoceFromUser();
							if(mark == -1)
							{
								subjectIdMap.remove(subjectIdMap.size()-1);
							}
							else if(mark == -2)
							{
								subjectIdMap.remove(subjectIdMap.size()-1);
								applicationService.updateAllMarks(studentId, subjectIdMap, subjectMark);
								return;
							}
							else
							{
								while(mark <= 0 || mark >= 100)
								{
									System.out.println("mark should be between 0 to 100.");
									mark = takeChoceFromUser();
								}
								subjectMark.add(mark);
							}
						}
					}
				}
				applicationService.updateAllMarks(studentId, subjectIdMap, subjectMark);
				break;
			}
			else
			{
				System.out.println("Sorry There are no subjects in the database to update");
			}	
		}
	}
	
	
	/**
	 * 
	 * @param applicationService
	 * @return
	 */
	private static int getInputDepartmentId(ApplicationService applicationService) {
		int departmentId;
		while(true)
		{
			try {
				printDepartment(applicationService);
				departmentId = getInputFromUser("Choose your department");
				applicationService.isDepartmentExist(departmentId);
				break;
			} catch (DepartmentNotFoundException ex) {
				printError(ex);
			} 
		}
		return departmentId;
	}
	
	
	/**
	 * 
	 * @return
	 */
	private static String getInputGender() {
		boolean checkChoice =  true;
		String gender = "";
		while(checkChoice)
		{
			int genderChoice = getInputFromUser("Student's gender\n1- Male\n2- Female");
			switch(genderChoice) {
				case 1:
					gender = "M";
					checkChoice = false;
					break;
				case 2:
					gender = "F";
					checkChoice = false;
					break;
				default:
					System.out.println("Please choose Only give option");		
			}
			
		}
		return gender;
	}
	
	
	/**
	 * 
	 * @param showMessage
	 * @return
	 */
	private static int getInputFromUser(String showMessage) {
		System.out.println(showMessage);
		int input = takeChoceFromUser();
		return input;
	}
	
	
	/**
	 * 
	 * @param ex
	 */
	public static void printError(Exception ex) {
		System.err.println(ex.getMessage());
		System.out.println("");
	}
	
	
	/**
	 * A method Than will validate all the Integer.
	 * @return
	 */
	private static int takeChoceFromUser() {
  
        int retValue=-1;
        
        while(true) {
        	try {
           	 retValue = Integer.parseInt(SCANNER.nextLine());
           	 break;
           } catch(NumberFormatException ex)
           {
           	System.err.println("That's not a valid Integer Please Enter Valid Input and Input should be in Integer Range!!\n");
           }
        }
        
        return retValue;
       
        
        
    }
    
	
	/**
	 * validate string space, number any other character are not allowed.
	 * @return
	 */
    private static String takeStringChoice() {
        
        boolean flag = true;
        String input="";
        while(flag)
        {
            input = SCANNER.nextLine();
            char[] ch=input.toCharArray();
            for(int index = 0; index < input.length();index++)
            {
                if((ch[index]>=65 && ch[index]<=90) || (ch[index]>=97 && ch[index]<=122))
                {
                    flag = false;
                }         
                else
                {
                    System.out.println("String Can not Have a special Charater or NUmbers");
                    System.out.println("Please Enter Valid Detail");
                    flag = true;
                    break;
                }
            }
            
        }
        return input;
        
    }
    
    
    /**
     * 
     * @param applicationService
     * @return
     */
    public static void printDepartment(ApplicationService applicationService) {
    	List<Map<String, Object>> departments = applicationService.getDepartments();
    	int count = 0;
    	for (Map<String, Object> department : departments) {
			for(Iterator<Map.Entry<String, Object>> iterator = department.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				if(key.equals("dep_name"))
				{
					count++;
					System.out.println(count + "-" + value);
				}
			}
    	}
    	return;
    }
	
	/**
	 * Some more features like BatchUpdate, List of Student Using RowMapper are implemented here.
	 * @param applicationService
	 * @param context
	 */
	
	private static void someMoreFeature(ApplicationService applicationService, AbstractApplicationContext context) {
		System.out.println("***************************************");
		System.out.println("List Of all Student Details using Row Mapper -- ");
		List<Student> students = applicationService.getAllStudents();
		if(students != null && !students.isEmpty())
		{
			for (Student record : students) {
				 System.out.println(record);
				
			 }
		}
		System.out.println("***************************************");
		System.out.println("Increase Every Student Marks by 4 of sub_id 1.");
		System.out.println("Batch Update Example");
		applicationService.updateBatchMarkByN(4);
		System.out.println("***************************************");
		System.out.println("Another Batch Update Example, Inserting 5 Student at once.");
		Student student1 = (Student) context.getBean("student");
		Student student2 = (Student) context.getBean("student");
		Student student3 = (Student) context.getBean("student");
		
		student1.setFirstName("Rajesh");
		student1.setLastName("Kumar");
		student1.setGender("M");
		student1.setDepartmentId(1);
		student1.setAddress("12hbdb");
		
		student2.setFirstName("Mukesh");
		student2.setLastName("Kumar");
		student2.setGender("M");
		student2.setDepartmentId(1);
		student2.setAddress("hjvash saha");
		
		student3.setFirstName("Ishita");
		student3.setLastName("Kalra");
		student3.setGender("F");
		student3.setDepartmentId(1);
		student3.setAddress("LNMIIT");
		
		List<Student> newStudents = new ArrayList<Student>();
		newStudents.add(student1);
		newStudents.add(student2);
		newStudents.add(student3);
		
		applicationService.addBatchStudents(newStudents);
		System.out.println("*****************************************");
		 
	}
	
	

}
