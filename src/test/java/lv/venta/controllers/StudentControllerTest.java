package lv.venta.controllers;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lv.venta.models.security.MyUser;
import lv.venta.models.users.Student;
import lv.venta.services.impl.security.MyUserDetailsManagerImpl;
import lv.venta.services.users.IStudentCRUDService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private MyUserDetailsManagerImpl userService;

    @Mock
    private IStudentCRUDService studentService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private List<Student> studentList;

    @BeforeEach
    void setup() {
        studentList = new ArrayList<>();
        Student student1 = new Student("Reinis", "Ricards", "123456-12345", new MyUser(), "348593849");
        Student student2 = new Student("Ricards", "Reinis", "654321-54321", new MyUser(), "495748399");
        studentList.add(student1);
        studentList.add(student2);
    }

    @Test
    void testShowAllStudents() {
        when(studentService.selectAllStudents()).thenReturn((ArrayList<Student>) studentList);

        String viewName = studentController.showAllStudents(model);

        verify(model).addAttribute(eq("student"), eq(studentList));
        assertEquals("student-all-page", viewName);
    }

    @Test
    void testShowStudentByMatriculaNo() throws Exception {
        String matriculaNo = "348593849";
        Student student = new Student("Reinis", "Ricards", "123456-12345", new MyUser(), matriculaNo);
        when(studentService.selectStudentByMatriculaNo(matriculaNo)).thenReturn(student);

        String viewName = studentController.showStudentByMatriculaNo(matriculaNo, model);

        verify(model).addAttribute(eq("MyStudentByMatriculaNo"), eq(student));
        assertEquals("student-one-page", viewName);
    }

    @Test
    void testRemoveStudentByMatriculaNo() throws Exception {
        String matriculaNo = "348593849";

        doNothing().when(studentService).deleteStudentByMatriculaNo(matriculaNo);

        String viewName = studentController.removeStudentByMatriculaNo(matriculaNo);

        verify(studentService).deleteStudentByMatriculaNo(matriculaNo);
        assertEquals("redirect:/student/showAll", viewName);
    }

    @Test
    void testInsertNewStudent() {
        List<MyUser> users = new ArrayList<>();
        when(userService.allUsers()).thenReturn(users);

        String viewName = studentController.insertNewStudent(model);

        verify(model).addAttribute(eq("users"), eq(users));
        verify(model).addAttribute(eq("student"), any(Student.class));
        assertEquals("student-add-page", viewName);
    }

    @Test
    void testInsertNewStudentPost() {
        Student student = new Student("Reinis", "Ricards", "123456-12345", new MyUser(), "M123");

        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(studentService).insertNewStudent(any(Student.class));

        String viewName = studentController.insertNewStudentPost(student, new MyUser(), bindingResult);

        assertEquals("redirect:/student/showAll", viewName);
    }

    @Test
    void testShowUpdateForm() throws Exception {
        String matriculaNo = "348593849";
        Student student = new Student("Reinis", "Ricards", "123456-12345", new MyUser(), matriculaNo);
        when(studentService.selectStudentByMatriculaNo(matriculaNo)).thenReturn(student);

        String viewName = studentController.showUpdateForm(matriculaNo, model);

        verify(model).addAttribute(eq("student"), eq(student));
        assertEquals("update-student", viewName);
    }

    @Test
    void testUpdateStudentByMatriculaNo() throws Exception {
        String matriculaNo = "348593849";
        Student student = new Student("Reinis", "Ricards", "123456-12345", new MyUser(), matriculaNo);

        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(studentService).updateStudentByMatriculaNo(eq(matriculaNo), any(Student.class));

        String viewName = studentController.updateStudentByMatriculaNo(matriculaNo, student, bindingResult);

        assertEquals("redirect:/student/show/" + matriculaNo, viewName);
    }
}
