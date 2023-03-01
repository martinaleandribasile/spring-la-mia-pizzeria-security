package com.java.pizzeria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping
	public String home() {
		return "redirect:pizze";
	}
	
	@GetMapping("/TLStest")
	public String TLStest(Authentication auth) {
		System.out.println("Login OK -- nome = " + auth.getName());
		return "TLStest";
	}

}
