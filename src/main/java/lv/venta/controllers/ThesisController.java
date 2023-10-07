package lv.venta.controllers;

import lv.venta.models.Thesis;
import lv.venta.services.IThesisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;

@Controller
@RequestMapping("/thesis")
public class ThesisController {

    @Autowired
    private IThesisCRUDService thesisService;

    @GetMapping("/showAll")
    public String showAllTheses(Model model) {
        ArrayList<Thesis> tempArray = thesisService.selectAllThesis();
        model.addAttribute("thesis", tempArray);
        return "thesis-all-page";
    }

    @GetMapping("/show/{thesis_id}")
    public String showThesisById(@PathVariable("thesis_id") long thesis_id, Model model) {
        try {
            model.addAttribute("MyThesisById", thesisService.selectThesisById(thesis_id));
            return "thesis-one-page";
        } catch (Exception e) {
            return "error-page";
        }
    }

    @PostMapping("/remove/{thesis_id}")
    public String removeThesisById(@PathVariable("thesis_id") long thesis_id) {
        try {
            thesisService.deleteThesis(thesis_id);
            return "redirect:/thesis/showAll";
        } catch (Exception e) {
            e.printStackTrace();
            return "error-page";
        }
    }

    @GetMapping("/insertNew")
    public String insertNewThesis(Thesis thesis) {
        return "thesis-add-page";
    }

    @PostMapping("/insertNew")
    public String insertNewThesis(@Valid Thesis thesis, BindingResult result) {
        if (!result.hasErrors()) {
            try {
                thesisService.insertNewThesis(thesis);
                return "redirect:/thesis/showAll";
            } catch (RuntimeException e) {
                return "error-page";
            }
        } else {
            System.out.println(result.getAllErrors());
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
            return "error-page";
        }
    }

    @PostMapping("/update/{thesis_id}")
    public String updateThesisById(@PathVariable("thesis_id") long thesis_id, @Valid Thesis thesis, BindingResult result) {
        if (result.hasErrors()) {
            return "thesis-update-page";
        } else {
            try {
                thesisService.updateThesis(thesis);
                return "redirect:/thesis/showAll"; 
            } catch (Exception e) {
                return "error-page";
            }
        }
    }
}
