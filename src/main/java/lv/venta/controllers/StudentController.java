package lv.venta.controllers;

import lv.venta.models.users.Student;
import lv.venta.services.users.IStudentCRUDService;
import lv.venta.services.users.impl.UserCRUDService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import lv.venta.models.users.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	UserCRUDService userService;
	
    @Autowired
    private IStudentCRUDService studentService;

    @GetMapping("/showAll")
    public String showAllStudents(Model model) {
    	ArrayList<Student> tempArray = studentService.selectAllStudents();
        model.addAttribute("student", tempArray);
        return "student-all-page";
    }

    @GetMapping("/show/{matriculaNo}")
    public String showStudentByMatriculaNo(@PathVariable("matriculaNo") String matriculaNo, Model model) {
        try {
            Student student = studentService.selectStudentByMatriculaNo(matriculaNo);
            if(student != null) {
                model.addAttribute("MyStudentByMatriculaNo", student);
                return "student-one-page";
            } 
        } catch (Exception e) {
            e.printStackTrace();  // Log the error
        }
        return "error-page";
    }

    @GetMapping("/remove/{matriculaNo}")
    public String removeStudentByMatriculaNo(@PathVariable("matriculaNo") String matriculaNo) {
        try {
            studentService.deleteStudentByMatriculaNo(matriculaNo);
            return "redirect:/student/showAll";
        } catch (Exception e) {
            return "error-page";
        }
    }



    @GetMapping("/insertNew")
    public String insertNewStudent(Model model) {
        List<User> users = userService.allUsers(); // Fetch all users
        model.addAttribute("users", users);
        model.addAttribute("student", new Student());
        return "student-add-page";
    }

    @PostMapping("/insertNew")
    public String insertNewStudentPost(@Valid @ModelAttribute("student") Student student,@ModelAttribute("user") User user, BindingResult result) {
        if (!result.hasErrors()) {
        	Student stud = new Student(
        			user.getPerson().getPersonName(),
        			user.getPerson().getSurname(),
        			user.getPerson().getPersonalCode(),
        			user,
        			student.getMatriculaNo());
            studentService.insertNewStudent(stud);
            return "redirect:/student/showAll";
        } else {
            return "error-page";
        }
    }

    @GetMapping("/update/{matriculaNo}")
    public String showUpdateForm(@PathVariable("matriculaNo") String matriculaNo, Model model) {
        try {
            Student student = studentService.selectStudentByMatriculaNo(matriculaNo);
            model.addAttribute("student", student);
            return "student-update-page";
        } catch (Exception e) {
            return "error-page";
        }
    }

    @PostMapping("/update/{matriculaNo}")
    public String updateStudentByMatriculaNo(@PathVariable("matriculaNo") String matriculaNo, @Valid Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "student-update-page";
        } else {
            try {
                studentService.updateStudentByMatriculaNo(matriculaNo, student);
                return "redirect:/student/show/" + student.getMatriculaNo();
            } catch (Exception e) {
                return "redirect:/error-page";
            }
        }
    }
}
