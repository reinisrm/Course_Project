package lv.venta.services.users;

import java.util.ArrayList;
import java.util.List;

import lv.venta.models.security.MyUser;
import lv.venta.models.users.Student;

public interface IStudentCRUDService {

	ArrayList<Student> selectAllStudents();
	
	Student selectStudentByMatriculaNo(String matriculaNo) throws Exception;
	
	void deleteStudentByMatriculaNo(String matriculaNo) throws Exception;
	
	public void insertNewStudent(Student student);
	
	void updateStudentByMatriculaNo(String matriculaNo, Student student) throws Exception;

	List<MyUser> getAllUsers();
	
	public Student findById(long id);
	
}
