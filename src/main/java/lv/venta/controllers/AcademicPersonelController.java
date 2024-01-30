package lv.venta.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import jakarta.validation.Valid;
import lv.venta.enums.Degree;
import lv.venta.models.users.Academic_personel;
import lv.venta.models.security.MyUser;
import lv.venta.services.impl.security.MyUserDetailsManagerImpl;
import lv.venta.services.users.impl.AcademicPersonelCRUDService;



@Controller
public class AcademicPersonelController {
	
	private static Logger logger = LogManager.getLogger(AcademicPersonelController.class);
	
	@Autowired
	AcademicPersonelCRUDService personelService;
	
	@Autowired
	MyUserDetailsManagerImpl userService;
	
	
	
	@GetMapping("/personel/showAll")
	public String showAllPersonel(Model model) {
		
		List<Academic_personel> academicPersonel = personelService.getAll();
		
		model.addAttribute("personel", academicPersonel);
		logger.info("Displaying all academic personnel.");
		return "show-all-personel";
		
	}
	
	
	@GetMapping("/personel/showOne/{id}")
	public String showOnePersonel(@PathVariable("id") int id, Model model) {
		
		try {
			
			Academic_personel temp = new Academic_personel();
			
			temp = personelService.findById(id);
			
			model.addAttribute("personel", temp);
			logger.info("Displaying details for academic personnel with ID: " + id);
			return "show-one-personel";
					
			
		
			
			
		}catch (Exception e) {
			logger.error("Error in showOnePersonel: " + e.getMessage());
		}
		
		
		return "error-page";
		
	}
	
	@GetMapping("/personel/delete/{id}")
	public String deletePersonel(@PathVariable("id") int id, Model model) {
		try {
			personelService.deletePersonelById(id);
			model.addAttribute("personel", personelService.getAll());
			logger.info("Deleting academic personnel with ID: " + id);		
			return "redirect:/personel/showAll";
			
		} catch (Exception e) {
			logger.error("Error in deletePersonel: " + e.getMessage());
		}
		
		return "error-page";
	}
	
	
	@GetMapping("/personel/add")
	public String createPersonel(Model model) {
		
		try {
		List<MyUser> users = userService.allUsers();
		
		Degree[] degrees = Degree.values();
		
		model.addAttribute("users", users);
		
		model.addAttribute("personel", new Academic_personel());
		
		model.addAttribute("degrees", degrees);
		} catch (Exception e) {
			logger.error("Error in createPersonel: " + e.getMessage());
		}
		
		
		return "insert-new-personel";
		
	}
	
	@PostMapping("/personel/add")
	public String createPersonelPost(@Valid Academic_personel personel, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			logger.error("Error in createPersonelPost: Validation failed");
	        return "error-page";
	    }
		
		Academic_personel pers = new Academic_personel(
				personel.getPersonName(), 
				personel.getSurname(), 
				personel.getPersonalCode(), 
				personel.getUser(), 
				personel.getDegree());
		
		
		personelService.insertNewPersonel(pers);
        logger.info("Academic Personel created: " + personel);
        logger.debug("Redirecting to showAll after creating academic personnel.");
		
		
		return "redirect:/personel/showAll";
	}
	
	
	@GetMapping("/personel/update/{id}")
    public String updatePersonelById(@PathVariable("id") int id, org.springframework.ui.Model model) {   

		try {
			Academic_personel temp = personelService.findById(id);
			Degree[] degrees = Degree.values();
			
			model.addAttribute("degrees", degrees);
			model.addAttribute("personel", temp);
			logger.info("Displaying details for academic personnel with ID: " + id + " for update.");
			return "update-personel";
		}catch (Exception e) {
			logger.error("Error in updatePersonelById: " + e.getMessage());
			return "error-page";
			
		}
        
    }

	@PostMapping("/personel/update/{id}")
	public String updatePersonelById2(@PathVariable int id, @Valid Academic_personel personel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			logger.error("Error in updatePersonelById2: Validation failed");
			return "update-personel";
		}

		if (id > 0) {
			personelService.updatePersonelById(id, personel);
			return "redirect:/personel/showAll";
		} else {
			logger.error("Error in updatePersonelById2: Invalid ID");
			return "error-page";
		}
	}
	
	
	@PostMapping("/personel/update/{id}")
	public String updateDriverById2(@PathVariable int id, @Valid Academic_personel personel, BindingResult bindingResult) {
		
	    if (bindingResult.hasErrors()) {
	    	logger.error("Error in updatePersonelById2: Validation failed");
	        return "update-driver";
	    }

	    if (id > 0) {
	    	
	        personelService.updatePersonelById(id, personel);
	        return "redirect:/personel/showAll";
	    } else {
	    	 logger.error("Error in updatePersonelById2: Invalid ID");
	    }

	    return "error-page";
	}
	
	
	

}
