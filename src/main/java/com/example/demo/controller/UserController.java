package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.entities.Users;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {
	@Autowired
	UserService service;

	@PostMapping("/registration")
	public String addUsers(@ModelAttribute Users user) {
		boolean userStatus = service.emailExists(user.getEmail());
		if (userStatus == false) {
			service.addUser(user);
			System.out.println("user added");
		} else {
			System.out.println("user already exists ");

		}

		return "home";

	}

	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,
			@RequestParam("password") String password ,
			HttpSession session ) {
		if (service.validateUser(email, password) == true) {
			String role = service.getRole(email);
			
			session.setAttribute("email", email);
			
			if(role.equals("Admin")) {
				return "adminHome";
			}
			return "customerHome";
		} else {
			return "login";
		}

	}
	
//	@GetMapping("/pay")
//	public String pay(@RequestParam String email) {
//		
//		boolean paymentStatus=false;//payment api
//		
//		if (paymentStatus == true) {
//			Users user = service.getUser(email);
//			user.setPremium(true);
//			service.updateUser(user);
//			
//		}
//		
//		
//		return "login";
//	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	

}
