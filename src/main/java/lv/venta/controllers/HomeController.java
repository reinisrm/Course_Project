
package lv.venta.controllers;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lv.venta.services.impl.security.MyUserDetailsManagerImpl;
import lv.venta.services.users.impl.PersonCRUDService;



@Controller
public class HomeController {
	
	
	@Autowired
	private MyUserDetailsManagerImpl userService;
	
	@Autowired
	private PersonCRUDService personService;
	
	
	@GetMapping("/home")
	public String homeScreen(Model model) {
		
		return "index";
		
	}

}
