package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lv.venta.enums.Degree;
import lv.venta.models.users.Academic_personel;
import lv.venta.models.users.User;
import lv.venta.services.users.impl.AcademicPersonelCRUDService;
import lv.venta.services.users.impl.UserCRUDService;

@Controller
public class AcademicPersonelController {
	
	@Autowired
	AcademicPersonelCRUDService personelService;
	
	@Autowired
	UserCRUDService userService;
	
	
	
	@GetMapping("/personel/showAll")
	private String showAllPersonel(Model model) {
		
		List<Academic_personel> academicPersonel = personelService.getAll();
		
		model.addAttribute("personel", academicPersonel);
		
		return "show-all-personel";
		
	}
	
	
	@GetMapping("/personel/showOne/{id}")
	private String showOnePersonel(@PathVariable("id") int id, Model model) {
		
		try {
			
			Academic_personel temp = new Academic_personel();
			
			temp = personelService.findById(id);
			
			model.addAttribute("personel", temp);
			
			return "show-one-personel";
					
			
		
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "error-page";
		
	}
	
	@GetMapping("/personel/delete/{id}")
	private String deletePersonel(@PathVariable("id") int id, Model model) {
		
		try {
			personelService.deletePersonelById(id);
			model.addAttribute("personel", personelService.getAll());
					
			return "redirect:/personel/showAll";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "error-page";
	}
	
	
	@GetMapping("/personel/add")
	private String createPersonel(Model model) {
		
		List<User> users = userService.allUsers();
		
		Degree[] degrees = Degree.values();
		
		model.addAttribute("users", users);
		
		model.addAttribute("personel", new Academic_personel());
		
		model.addAttribute("degrees", degrees);
		
		
		return "insert-new-personel";
		
	}
	
	@PostMapping("/personel/add")
	private String createPersonelPost(@Valid Academic_personel personel, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
	        
	        return "error-page";
	    }
		
		Academic_personel pers = new Academic_personel(
				personel.getPersonName(), 
				personel.getSurname(), 
				personel.getPersonalCode(), 
				personel.getUser(), 
				personel.getDegree());
		
		
		personelService.insertNewPersonel(pers);
		
		
		return "redirect:/personel/showAll";
	}
	
	
	@GetMapping("/personel/update/{id}")
    public String updatePersonelById(@PathVariable("id") int id, org.springframework.ui.Model model) {
        

		try {
			Academic_personel temp = personelService.findById(id);
			Degree[] degrees = Degree.values();
			
			model.addAttribute("degrees", degrees);
			model.addAttribute("personel", temp);
			return "update-personel";
		}catch (Exception e) {
			
			return "error-page";
			
		}
        
    }
	
	
	@PostMapping("/personel/update/{id}")
	public String updateDriverById2(@PathVariable int id, @Valid Academic_personel personel, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        return "update-driver";
	    }

	    if (id > 0) {
	    	
	        personelService.updatePersonelById(id, personel);
	        return "redirect:/personel/showAll";
	    }

	    return "error-page";
	}
	
	
	

}
