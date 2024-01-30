package lv.venta.controllers;

import lv.venta.models.security.MyUser;
import lv.venta.models.users.Academic_personel;
import lv.venta.services.impl.security.MyUserDetailsManagerImpl;
import lv.venta.services.users.impl.AcademicPersonelCRUDService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import lv.venta.enums.Degree;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcademicPersonelControllerTest {

    @InjectMocks
    private AcademicPersonelController academicPersonelController;

    @Mock
    private AcademicPersonelCRUDService personelService;

    @Mock
    private MyUserDetailsManagerImpl userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private List<Academic_personel> personelList;

    @BeforeEach
    void setup() {
        personelList = new ArrayList<>();
        Academic_personel personel1 = new Academic_personel("John", "Doe", "123456-12345", new MyUser(), Degree.BACHELOR);
        Academic_personel personel2 = new Academic_personel("Jane", "Doe", "654321-54321", new MyUser(), Degree.MASTER);
        personelList.add(personel1);
        personelList.add(personel2);
    }

    @Test
    void testShowAllPersonel() {
        when(personelService.getAll()).thenReturn(personelList);

        String viewName = academicPersonelController.showAllPersonel(model);

        verify(model).addAttribute(eq("personel"), eq(personelList));
        assertEquals("show-all-personel", viewName);
    }

    @Test
    void testShowOnePersonel() throws Exception {
        int id = 1;
        Academic_personel personel = new Academic_personel("John", "Doe", "123456-12345", new MyUser(), Degree.BACHELOR);
        when(personelService.findById(id)).thenReturn(personel);

        String viewName = academicPersonelController.showOnePersonel(id, model);

        verify(model).addAttribute(eq("personel"), eq(personel));
        assertEquals("show-one-personel", viewName);
    }

    @Test
    void testDeletePersonel() throws Exception {
        int id = 1;
        doNothing().when(personelService).deletePersonelById(id);
        when(personelService.getAll()).thenReturn(personelList);

        String viewName = academicPersonelController.deletePersonel(id, model);

        verify(personelService).deletePersonelById(id);
        verify(model).addAttribute(eq("personel"), eq(personelList));
        assertEquals("redirect:/personel/showAll", viewName);
    }

    @Test
    void testCreatePersonel() {
        List<MyUser> users = new ArrayList<>();
        when(userService.allUsers()).thenReturn(users);

        String viewName = academicPersonelController.createPersonel(model);

        verify(model).addAttribute(eq("users"), eq(users));
        verify(model).addAttribute(eq("personel"), any(Academic_personel.class));
        verify(model).addAttribute(eq("degrees"), eq(Degree.values()));
        assertEquals("insert-new-personel", viewName);
    }

    @Test
    void testCreatePersonelPost() {
        Academic_personel personel = new Academic_personel("John", "Doe", "123456-12345", new MyUser(), Degree.BACHELOR);

        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(personelService).insertNewPersonel(any(Academic_personel.class));

        String viewName = academicPersonelController.createPersonelPost(personel, bindingResult);

        verify(personelService).insertNewPersonel(personel);
        assertEquals("redirect:/personel/showAll", viewName);
    }

    @Test
    void testCreatePersonelPostWithErrors() {
        Academic_personel personel = new Academic_personel();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = academicPersonelController.createPersonelPost(personel, bindingResult);

        assertEquals("error-page", viewName);
    }

    @Test
    void testUpdatePersonelById() throws Exception {
        int id = 1;
        Academic_personel personel = new Academic_personel("John", "Doe", "123456-12345", new MyUser(), Degree.BACHELOR);
        when(personelService.findById(id)).thenReturn(personel);

        String viewName = academicPersonelController.updatePersonelById(id, model);

        verify(model).addAttribute(eq("personel"), eq(personel));
        verify(model).addAttribute(eq("degrees"), eq(Degree.values()));
        assertEquals("update-personel", viewName);
    }

    @Test
    void testUpdatePersonelById2() throws Exception {
        int id = 1;
        Academic_personel personel = new Academic_personel("John", "Doe", "123456-12345", new MyUser(), Degree.BACHELOR);

        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(personelService).updatePersonelById(eq(id), any(Academic_personel.class));

        String viewName = academicPersonelController.updateDriverById2(id, personel, bindingResult);

        verify(personelService).updatePersonelById(eq(id), eq(personel));
        assertEquals("redirect:/personel/showAll", viewName);
    }

    @Test
    void testUpdatePersonelById2WithErrors() throws Exception {
        int id = 1;
        Academic_personel personel = new Academic_personel();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = academicPersonelController.updateDriverById2(id, personel, bindingResult);

        assertEquals("update-driver", viewName);
    }


}