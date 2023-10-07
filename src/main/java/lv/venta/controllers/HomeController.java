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
import lv.venta.models.users.User;
import lv.venta.services.users.impl.PersonCRUDService;
import lv.venta.services.users.impl.UserCRUDService;

@Controller
public class HomeController {
	
	@Autowired
	private UserCRUDService userService;
	
	@Autowired
	private PersonCRUDService personService;
	
	
	@GetMapping("/home")
	private String homeScreen(Model model) {
		
		return "index";
		
	}

	
	
	@GetMapping("/login")
	private String login(Model model) {
		return "login";
	}
	
	@PostMapping("/login")
	private String loginPost(@RequestParam("email") String email, @RequestParam("password") String password,
			Model model) {

		try {

			User temp = userService.findByEmail(email);

			if (temp != null) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				if (passwordEncoder.matches(password, temp.getPassword())) {

					model.addAttribute("person", temp.getPerson());
					return "index";
				} else {
					model.addAttribute("error_message", "Incorrect password or email");
					return "login";
				}
			} else {
				model.addAttribute("error_message", "Incorrect password or email");
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("user", new User());

		return "error-page";
	}

	
	@GetMapping("/register")
	public String register(Model model) {
		
	    
	    Person tempPerson = new Person();
	    
	    model.addAttribute("person", tempPerson);
	    
	    
	    return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(
	        @Valid @ModelAttribute("person") Person person,
	        BindingResult bindingResult,
	        Model model) {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("error_message", bindingResult.getFieldError().getDefaultMessage());
	        return "register";
	    }

	    try {
	        if (userService.findByEmail(person.getUser().getEmail()) == null) {
	            User user = new User();
	            user.setPassword(person.getUser().getPassword());
	            user.setEmail(person.getUser().getEmail());


	            userService.registerUser(user);


	            person.setUser(user);
	            personService.reigsterPerson(person);

	            model.addAttribute("person", person);

	            return "index";
	        } else {
	            model.addAttribute("error_message", "Šāds e-pasts jau eksistē");
	            return "register";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return "error-page";
	}




	
	

}
