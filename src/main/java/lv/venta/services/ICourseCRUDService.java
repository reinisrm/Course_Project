package lv.venta.services;

import java.util.List;

import lv.venta.models.Course;
import lv.venta.models.users.Student;

public interface ICourseCRUDService {

	public List<Course> getAll();
	
	public void deleteCourseById(long id);
	
	public void updateCourseById(long id, Course course);
	
	public void insertNewCourse(Course course);
	
	public List<Student> getDebtStudents(Course course);
	
	public Course findCourseById(long id);
	
	public void addDebtById(long courseId, long studentId) throws Exception;

	public void removeDebtFromCourse(long courseId, long studentId) throws Exception;

	
	
}
