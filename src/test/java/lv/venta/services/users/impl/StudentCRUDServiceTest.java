package lv.venta.services.users.impl;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.venta.models.security.MyUser;
import lv.venta.models.users.Student;
import lv.venta.repos.users.IRepoStudent;
import lv.venta.services.impl.CommentsCRUDService;
import lv.venta.services.users.IStudentCRUDService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentCRUDServiceTest {

    @InjectMocks
    private StudentCRUDService studentService;

    @Mock
    private IRepoStudent studentRepo;

    @Mock
    private CommentsCRUDService commentsService;

    @Mock
    private MyUser user;

    private List<Student> studentList;

    @BeforeEach
    void setup() {
        studentList = new ArrayList<>();
        Student student1 = new Student("Reinis", "Ricards", "123456-12345", user, "459305741");
        Student student2 = new Student("Ricards", "Reinis", "654321-54321", user, "795746335");
        studentList.add(student1);
        studentList.add(student2);
    }

    @Test
    void testSelectAllStudents() {
        when(studentRepo.findAll()).thenReturn(studentList);

        List<Student> result = studentService.selectAllStudents();

        assertEquals(studentList, result);
    }

    @Test
    void testSelectStudentByMatriculaNo() throws Exception {
        String matriculaNo = "795746335";
        Student student = new Student("Ricards", "Reinis", "654321-54321", user, matriculaNo);
        when(studentRepo.findAll()).thenReturn(studentList);

        doNothing().when(studentRepo).delete(any());

        Student result = studentService.selectStudentByMatriculaNo(matriculaNo);

        assertEquals(student, result);
    }

    @Test
    void testDeleteStudentByMatriculaNo() throws Exception {
        String matriculaNo = "459305741";
        Student student = new Student("Reinis", "Ricards", "123456-12345", user, matriculaNo);
        when(studentRepo.findAll()).thenReturn(studentList);
        doNothing().when(studentRepo).delete(any());

        assertDoesNotThrow(() -> studentService.deleteStudentByMatriculaNo(matriculaNo));
    }

    @Test
    void testInsertNewStudent() {
        String matriculaNo = "451305748";
        Student student = new Student("Reinis", "Ricards", "123456-12345", user, matriculaNo);
        when(studentRepo.findAll()).thenReturn(studentList);

        assertDoesNotThrow(() -> studentService.insertNewStudent(student));
    }

    @Test
    void testUpdateStudentByMatriculaNo() throws Exception {
        String matriculaNo = "459305708";
        Student expectedStudent = new Student("Reinis", "Ricards", "123456-12345", user, matriculaNo);
        when(studentRepo.findAll()).thenReturn(studentList);

        assertDoesNotThrow(() -> studentService.updateStudentByMatriculaNo(matriculaNo, expectedStudent));

        // verify that update has occurred
        verify(studentRepo, times(1)).save(any());
    }

    @Test
    void testUpdateStudentByMatriculaNo_InvalidMatriculaNo() {
        String matriculaNo = "InvalidMatriculaNo";
        Student student = new Student("Reinis", "Ricards", "123456-12345", user, matriculaNo);
        when(studentRepo.findAll()).thenReturn(studentList);

        assertThrows(Exception.class, () -> studentService.updateStudentByMatriculaNo(matriculaNo, student));
    }
}
