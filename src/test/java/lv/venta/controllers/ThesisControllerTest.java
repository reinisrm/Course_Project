package lv.venta.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lv.venta.models.Thesis;
import lv.venta.services.IThesisCRUDService;

@ExtendWith(MockitoExtension.class)
class ThesisControllerTest {

    @InjectMocks
    private ThesisController thesisController;

    @Mock
    private IThesisCRUDService thesisService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private ArrayList<Thesis> thesisList;

    @BeforeEach
    void setup() {
        thesisList = new ArrayList<>();
        thesisList.add(new Thesis());
        thesisList.add(new Thesis());
    }

    @Test
    void testShowAllTheses() {
        when(thesisService.selectAllThesis()).thenReturn(thesisList);

        String viewName = thesisController.showAllTheses(model);

        verify(model).addAttribute(eq("thesis"), eq(thesisList));
        assertEquals("thesis-all-page", viewName);
    }

    @Test
    void testShowThesisById() throws Exception {
        long thesisId = 1L;
        Thesis thesis = new Thesis(/* parameters for the thesis constructor */);
        when(thesisService.selectThesisById(thesisId)).thenReturn(thesis);

        String viewName = thesisController.showThesisById(thesisId, model);

        verify(model).addAttribute(eq("MyThesisById"), eq(thesis));
        assertEquals("thesis-one-page", viewName);
    }

    @Test
    void testRemoveThesisById() throws Exception {
        long thesisId = 1L;
        doNothing().when(thesisService).deleteThesis(thesisId);

        String viewName = thesisController.removeThesisById(thesisId);

        verify(thesisService).deleteThesis(thesisId);
        assertEquals("redirect:/thesis/showAll", viewName);
    }

    @Test
    void testInsertNewThesisPost() {
        Thesis thesis = new Thesis();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = thesisController.insertNewThesis(thesis, bindingResult);

        verify(thesisService).insertNewThesis(thesis);
        assertEquals("redirect:/thesis/showAll", viewName);
    }

    @Test
    void testInsertNewThesisPostWithErrors() {
        Thesis thesis = new Thesis();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = thesisController.insertNewThesis(thesis, bindingResult);

        assertEquals("error-page", viewName);
    }

    @Test
    void testUpdateThesisByIdGet() throws Exception {
        long thesisId = 1L;
        Thesis thesis = new Thesis();
        when(thesisService.selectThesisById(thesisId)).thenReturn(thesis);

        String viewName = thesisController.updateThesisById(thesisId, model);

        verify(model).addAttribute(eq("thesis"), eq(thesis));
        assertEquals("update-thesis", viewName);
    }

    @Test
    void testUpdateThesisByIdPost() throws Exception {
        long thesisId = 1L;
        Thesis thesis = new Thesis();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = thesisController.updateThesisById(thesisId, thesis, bindingResult);

        verify(thesisService).updateThesis(thesis, thesisId);
        assertEquals("redirect:/thesis/showAll", viewName);
    }

    @Test
    void testUpdateThesisByIdPostWithErrors() {
        long thesisId = 1L;
        Thesis thesis = new Thesis();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = thesisController.updateThesisById(thesisId, thesis, bindingResult);

        assertEquals("update-thesis", viewName);
    }


}
