package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.validation.Valid;
import lv.venta.models.Course;
import lv.venta.models.users.Student;
import lv.venta.services.impl.CourseCRUDService;
import lv.venta.services.users.impl.StudentCRUDService;


@Controller
@RequestMapping("/courses")
public class CourseController {
	
	private static final Logger logger = LogManager.getLogger(CourseController.class);
	
	@Autowired
	CourseCRUDService courseService;
	
	@Autowired
	StudentCRUDService studentService;
	
	@GetMapping("/showAll")
	public String showAllCourses(Model model) {
		
		List<Course> courses = courseService.getAll();
		
		model.addAttribute("courses", courses);
		
		return "show-all-courses";
		
		
	}
	
	
	@GetMapping("/showOne/{id}")
	public String showOneCourse(@PathVariable("id") long id,Model model) {
		try {
		
			Course course = courseService.findCourseById(id);
			
			List<Student> students = studentService.selectAllStudents();
			
			List<Student> debt = courseService.getDebtStudents(null);
			
			model.addAttribute("courses", course);
			
			model.addAttribute("students", students);
			
			return "show-one-courses";
		} catch (Exception e) {
			logger.error("Error in showOneCourse: " + e.getMessage());
			return "error-page";
		}
		
		
	}
	
	@GetMapping("/add")
	public String insertNewCourse(Model model) {
		try {
			model.addAttribute("courses", new Course());
			return "insert-new-course";
		} catch (Exception e) {
			logger.error("Error in insertNewCourse: " + e.getMessage());
			return "error-page";
		}
		
	}
	
	@PostMapping("/add")
	public String insertNewCourse2(@Valid Course course, BindingResult bindingResult) {
		try { 
			if (bindingResult.hasErrors()) {
		        
		        return "error-page";
		    }
			
			Course temp = new Course(course.getTitle(), course.getCreditPoints());
			
			courseService.insertNewCourse(temp);
			
			return "redirect:/courses/showAll";
		} catch (Exception e) {
			logger.error("Error in insertNewCourse: " + e.getMessage());
			return "error-page";
		}
		
	}
	
	
	//TODO update
	
		
	@PostMapping("/addDebt/{courseId}/{studentId}")
	public String addDebt(@PathVariable long courseId,@PathVariable long studentId) {
		try {
			courseService.addDebtById(courseId, studentId);
			return "redirect:/courses/showOne/{courseId}";
		} catch (Exception e) {
			logger.error("Error in addDebt: " + e.getMessage());
			return "error-page";
		}
	}
	
	@PostMapping("/removeDebt/{courseId}/{studentId}")
	public String removeDebt(@PathVariable long courseId, @PathVariable long studentId) {
	    try {
	        courseService.removeDebtFromCourse(courseId, studentId);
	        return "redirect:/courses/showOne/{courseId}";
	    } catch (Exception e) {
	    	logger.error("Error in removeDebt: " + e.getMessage());
	    
	    return "error-page";
	    }
	}

	
	

}
