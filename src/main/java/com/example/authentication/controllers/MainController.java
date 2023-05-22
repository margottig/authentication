package com.example.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.authentication.models.LoginUser;
import com.example.authentication.models.User;
import com.example.authentication.services.UserService;

import jakarta.validation.Valid;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Model viewModel) {
		viewModel.addAttribute("user", new User());
		viewModel.addAttribute("login", new LoginUser());
		return "loginreg.jsp";
	}
	
	@PostMapping("/registration")
	public String register(@Valid @ModelAttribute("user") User usuario, 
			BindingResult resultado, Model viewModel) {
		if(resultado.hasErrors()) {
//			viewModel.addAttribute("user", new User());
			viewModel.addAttribute("login", new LoginUser());
			return "loginreg.jsp";
		}
		userService.registerUser(usuario);
		return "redirect:/dashboard";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("login") LoginUser loginuser, 
			BindingResult resultado, Model viewModel) {
		if(resultado.hasErrors()) {
			viewModel.addAttribute("user", new User());
//			viewModel.addAttribute("login", new LoginUser());
			return "loginreg.jsp";
		}
		if(userService.authenticateUser(loginuser.getEmail(), 
				loginuser.getPassword())) {
			return "redirect:/dashboard";
			
		}else {
			viewModel.addAttribute("user", new User());
			resultado.rejectValue("email", "Matches", " Contraseña/Email no válido");
			return "loginreg.jsp";
			
		}
	}
	
	@GetMapping("/dashboard")
	public String welcome() {
		return "dashboard.jsp";
	}

}
