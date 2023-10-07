package lv.venta.services.users;

import java.util.ArrayList;
import java.util.List;

import lv.venta.models.Comments;
import lv.venta.models.users.Student;
import lv.venta.models.users.User;

public interface IStudentCRUDService {

	ArrayList<Student> selectAllStudents();
	
	Student selectStudentByMatriculaNo(String matriculaNo) throws Exception;
	
	void deleteStudentByMatriculaNo(String matriculaNo) throws Exception;
	
	public void insertNewStudent(Student student);
	
	void updateStudentByMatriculaNo(String matriculaNo, Student student) throws Exception;

	List<User> getAllUsers();
	
	public Student findById(long id);
	
}
