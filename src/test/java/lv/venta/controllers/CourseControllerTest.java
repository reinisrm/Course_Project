package lv.venta.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import lv.venta.models.Course;
import lv.venta.models.users.Student;
import lv.venta.services.impl.CourseCRUDService;
import lv.venta.services.users.impl.StudentCRUDService;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseCRUDService courseService;

    @Mock
    private StudentCRUDService studentService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private List<Course> courseList;

    @BeforeEach
    void setup() {
        courseList = new ArrayList<>();
        courseList.add(new Course());
        courseList.add(new Course());
    }

    @Test
    void testShowAllCourses() {
        when(courseService.getAll()).thenReturn(courseList);

        String viewName = courseController.showAllCourses(model);

        verify(model).addAttribute(eq("courses"), eq(courseList));
        assertEquals("show-all-courses", viewName);
    }

    @Test
    void testShowOneCourse() {
        long courseId = 1L;
        Course course = new Course();
        List<Student> students = new ArrayList<>();
        students.add(new Student());

        when(courseService.findCourseById(courseId)).thenReturn(course);
        when(studentService.selectAllStudents()).thenReturn((ArrayList<Student>) students);

        String viewName = courseController.showOneCourse(courseId, model);

        verify(model).addAttribute(eq("courses"), eq(course));
        verify(model).addAttribute(eq("students"), eq(students));
        assertEquals("show-one-courses", viewName);
    }

    @Test
    void testInsertNewCourseGet() {
        String viewName = courseController.insertNewCourse(model);

        verify(model).addAttribute(eq("course"), any(Course.class));
        assertEquals("insert-new-course", viewName);
    }

    @Test
    void testInsertNewCoursePost() {
        Course course = new Course();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = courseController.insertNewCourse2(course, bindingResult);

        verify(courseService).insertNewCourse(any(Course.class));
        assertEquals("redirect:/courses/showAll", viewName);
    }

    @Test
    void testInsertNewCoursePostWithErrors() {
        Course course = new Course();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = courseController.insertNewCourse2(course, bindingResult);

        assertEquals("insert-new-course", viewName);
    }
    @Test
    void testAddDebt() throws Exception {
        long courseId = 1L;
        long studentId = 1L;

        doNothing().when(courseService).addDebtById(courseId, studentId);

        String viewName = courseController.addDebt(courseId, studentId);

        verify(courseService).addDebtById(courseId, studentId);
        assertEquals("redirect:/courses/showOne/" + courseId, viewName);
    }

    @Test
    void testAddDebtException() throws Exception {
        long courseId = 1L;
        long studentId = 1L;

        doThrow(new RuntimeException("Error")).when(courseService).addDebtById(courseId, studentId);

        String viewName = courseController.addDebt(courseId, studentId);

        assertEquals("error-page", viewName);
    }

    @Test
    void testRemoveDebt() throws Exception {
        long courseId = 1L;
        long studentId = 1L;

        doNothing().when(courseService).removeDebtFromCourse(courseId, studentId);

        String viewName = courseController.removeDebt(courseId, studentId);

        verify(courseService).removeDebtFromCourse(courseId, studentId);
        assertEquals("redirect:/courses/showOne/" + courseId, viewName);
    }

    @Test
    void testRemoveDebtException() throws Exception {
        long courseId = 1L;
        long studentId = 1L;

        doThrow(new RuntimeException("Error")).when(courseService).removeDebtFromCourse(courseId, studentId);

        String viewName = courseController.removeDebt(courseId, studentId);

        assertEquals("error-page", viewName);
    }

}
