package lv.venta.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Course;
import lv.venta.models.users.Student;
import lv.venta.repos.IRepoCourse;
import lv.venta.repos.users.IRepoStudent;
import lv.venta.services.ICourseCRUDService;
import lv.venta.services.users.IStudentCRUDService;

@Service
public class CourseCRUDService implements ICourseCRUDService {

	@Autowired
	IRepoCourse courseRepo;
	
	@Autowired
	IRepoStudent studentRepo;
	
	@Autowired
	IStudentCRUDService studentService;

	@Override
	public List<Course> getAll() {

		return (List<Course>) courseRepo.findAll();
	}

	@Override
	public void deleteCourseById(long id) {

		try {

			for (Course course : getAll()) {
				if (course.getCourseId() == id) {
					courseRepo.delete(course);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void updateCourseById(long id, Course course) {

		try {
			Course temp = new Course();

			for (Course cours : getAll()) {
				if (cours.getCourseId() == id) {

					temp = cours;

					temp.setTitle(course.getTitle());
					temp.setCreditPoints(course.getCreditPoints());
					if (cours.getStudentsWithDebt() != null) {
						temp.setStudentsWithDebt(course.getStudentsWithDebt());
					}

					courseRepo.save(temp);

					break;

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	
	@Override
	public void insertNewCourse(Course course) {

		try {

			if (course != null) {
				for (Course courses : getAll()) {
					if (!courses.getTitle().equals(course.getTitle())) {
						courseRepo.save(course);
						return;
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public List<Student> getDebtStudents(Course course) {

		try {

			List<Student> debt = new ArrayList<>();

			if (course.getStudentsWithDebt() != null) {
				debt.add((Student) course.getStudentsWithDebt());
				return debt;
			}

			return null;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;

	}
	
	
	public Course findCourseById(long id) {
		try {
			
			for(Course course: getAll()) {
				if(course.getCourseId() == id) {
					return course;
				}
			}
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	@Override
	public void addDebtById(long courseId, long studentId) throws Exception {
	
		
		try {
			if(findCourseById(courseId)==null) {
				throw new Exception("Kurss netika atrasts!");
				
			}
			
			if(studentService.findById(studentId) == null) {
				throw new Exception("Students netika atrasts!");
			}
			
			//Jauni mainigie
			
			List<Student> debt = new ArrayList<>();
			
			Course temp = findCourseById(courseId);
			
			Student tempStud = studentService.findById(studentId);
			
			//Darbibas!
			
			debt = (List<Student>) temp.getStudentsWithDebt();
			
			List<Course> studentDebt = (List<Course>) tempStud.getDebtCourses();
			
			if(!debt.contains(tempStud) && !studentDebt.contains(temp)) {
				
				debt.add(tempStud);
				
				studentDebt.add(temp);
				
				temp.setStudentsWithDebt(debt);
				
				tempStud.setDebt(true);
				
				tempStud.setDebtCourses(studentDebt);
				
				studentRepo.save(tempStud);
				
				courseRepo.save(temp);
				
			}
			else {
				throw new Exception("Studenta parāds jau ir registrēts!");
			}
			
			
			
		} catch (Exception e) {
			throw new Exception("Kaut kas nogāja greizi !");
		}
		
	}
	
	@Override
	public void removeDebtFromCourse(long courseId, long studentId) throws Exception {
	    try {
	        Course course = findCourseById(courseId);
	        if (course == null) {
	            throw new Exception("Kurss netika atrasts!");
	        }
	        
	        Student student = studentService.findById(studentId);
	        if (student == null) {
	            throw new Exception("Students netika atrasts!");
	        }
	        
	        List<Student> debtStudents = (List<Student>) course.getStudentsWithDebt();
	        List<Course> studentDebtCourses = (List<Course>) student.getDebtCourses();
	        
	        if (debtStudents.contains(student) && studentDebtCourses.contains(course)) {
	            debtStudents.remove(student);
	            studentDebtCourses.remove(course);
	            
	            course.setStudentsWithDebt(debtStudents);
	            student.setDebtCourses(studentDebtCourses);
	            
	            if (studentDebtCourses.isEmpty()) {
	                student.setDebt(false);
	            }
	            
	            studentRepo.save(student);
	            courseRepo.save(course);
	        } else {
	            throw new Exception("Studenta parāds nav reģistrēts!");
	        }
	    } catch (Exception e) {
	        throw new Exception("Kaut kas nogāja greizi!");
	    }
	}

	
	
	

}
