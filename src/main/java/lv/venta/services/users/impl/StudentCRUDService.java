package lv.venta.services.users.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.User;
import lv.venta.models.users.Student;
import lv.venta.repos.IRepoComments;
import lv.venta.repos.users.IRepoStudent;
import lv.venta.repos.users.IRepoUser;
import lv.venta.services.impl.CommentsCRUDService;
import lv.venta.services.users.IStudentCRUDService;

@Service
public class StudentCRUDService implements IStudentCRUDService{

	@Autowired
	IRepoUser userRepo;
	@Autowired
	IRepoStudent studentRepo;
	@Autowired
	CommentsCRUDService commentsService;
	@Autowired
	IRepoComments commentsRepo;
	
	@Override
	public ArrayList<Student> selectAllStudents() {
		return (ArrayList<Student>) studentRepo.findAll();
	}
	@Override
	public List<User> getAllUsers() {
	    return (List<User>) userRepo.findAll();
	}

	
	@Override
	public Student selectStudentByMatriculaNo(String matriculaNo) throws Exception {
		for (Student temp : selectAllStudents()) {
			if (temp.getMatriculaNo().equals(matriculaNo)) {
				return temp;
			}
		}
		throw new Exception("Nepareizs matrikulasNo");
	}
	
	@Override
	public void deleteStudentByMatriculaNo(String matriculaNo) throws Exception {
	    Student studentToDelete = null;
	    for (Student temp : selectAllStudents()) {
	        if(temp.getMatriculaNo().equals(matriculaNo)) {
	            studentToDelete = temp;
	            break;
	        }
	    } 
	    if(studentToDelete != null) {
	        studentRepo.delete(studentToDelete);
	    } else {
	        throw new Exception("Nepareizs matrikulasNo");
	    }
	}
	@Override
	public void insertNewStudent(Student student) {
	    for(Student temp : selectAllStudents()) {
	        if(temp.getMatriculaNo().equals(student.getMatriculaNo())) {
	            throw new RuntimeException("Students ar šādu matrikulas numuru jau eksistē");
	        }
	    }
	    studentRepo.save(student);
	}


	@Override
	public void updateStudentByMatriculaNo(String matriculaNo, Student inputStudent) throws Exception {
	    List<Student> allStudents = (List<Student>) studentRepo.findAll();
	    Optional<Student> optionalStudent = allStudents.stream()
	            .filter(student -> student.getMatriculaNo().equals(matriculaNo))
	            .findFirst();
	    if (optionalStudent.isPresent()) {
	        Student studentToUpdate = optionalStudent.get();
	        studentToUpdate.setPersonName(inputStudent.getPersonName());
	        studentToUpdate.setSurname(inputStudent.getSurname());
	        studentToUpdate.setPersonalCode(inputStudent.getPersonalCode());
	        studentToUpdate.setUser(inputStudent.getUser());
	        studentToUpdate.setMatriculaNo(inputStudent.getMatriculaNo());
	        studentRepo.save(studentToUpdate);
	    } else {
	        throw new Exception("Nepareizs matrikulasNo");
	    }
	}
	@Override
	public Student findById(long id) {
		
		
		for(Student student: selectAllStudents()) {
			if(student.getPersonId() == id) {
				return student;
			}
		}
		
		return null;
		
	}
	
	
	
}
