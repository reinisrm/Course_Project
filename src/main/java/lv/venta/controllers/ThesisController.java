package lv.venta.controllers;

import lv.venta.models.Thesis;
import lv.venta.services.IThesisCRUDService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/thesis")
public class ThesisController {

    private static final Logger logger = LogManager.getLogger(ThesisController.class);

    @Autowired
    private IThesisCRUDService thesisService;

    @GetMapping("/showAll")
    public String showAllTheses(Model model) {
        List<Thesis> thesisList = thesisService.selectAllThesis();
        model.addAttribute("thesis", thesisList);
        return "thesis-all-page";
    }

    @GetMapping("/show/{thesis_id}")
    public String showThesisById(@PathVariable("thesis_id") long thesis_id, Model model) {
        try {
            Thesis thesis = thesisService.selectThesisById(thesis_id);
            model.addAttribute("MyThesisById", thesis);
            return "thesis-one-page";
        } catch (Exception e) {
            logger.error("Error in showThesisById: {}", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/remove/{thesis_id}")
    public String removeThesisById(@PathVariable("thesis_id") long thesis_id) {
        try {
            thesisService.deleteThesis(thesis_id);
            return "redirect:/thesis/showAll";
        } catch (Exception e) {
            logger.error("Error in removeThesisById: {}", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/insertNew")
    public String insertNewThesis(Model model) {
        model.addAttribute("thesis", new Thesis());
        return "thesis-add-page";
    }

    @PostMapping("/insertNew")
    public String insertNewThesis(@Valid Thesis thesis, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Error in insertNewThesis: Validation failed");
            return "thesis-add-page";
        }

        try {
            thesisService.insertNewThesis(thesis);
            return "redirect:/thesis/showAll";
        } catch (RuntimeException e) {
            logger.error("Error in insertNewThesis: {}", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/update/{thesis_id}")
    public String updateThesisById(@PathVariable("thesis_id") long thesis_id, Model model) {
        try {
            Thesis thesis = thesisService.selectThesisById(thesis_id);
            model.addAttribute("thesis", thesis);
            return "update-thesis";
        } catch (Exception e) {
            logger.error("Error in updateThesisById: {}", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{thesis_id}")
    public String updateThesisById(@PathVariable("thesis_id") long thesis_id, @Valid Thesis thesis, BindingResult result) {
        if (result.hasErrors()) {
            return "update-thesis";
        }

        try {
            thesisService.updateThesis(thesis, thesis_id);
            return "redirect:/thesis/showAll";
        } catch (Exception e) {
            logger.error("Error in updateThesisById: {}", e.getMessage());
            return "error-page";
        }
    }
}
