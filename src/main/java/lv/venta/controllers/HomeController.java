
package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lv.venta.models.users.Person;
import lv.venta.services.impl.security.MyUserDetailsManagerImpl;
import lv.venta.services.users.impl.PersonCRUDService;



@Controller
public class HomeController {
	
	
	@Autowired
	private MyUserDetailsManagerImpl userService;
	
	@Autowired
	private PersonCRUDService personService;
	
	
	@GetMapping("/home")
	private String homeScreen(Model model) {
		
		return "index";
		
	}

}
