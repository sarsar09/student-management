package com.ai.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
	    
		session.removeAttribute("userId");	
	    session.removeAttribute("name");	
	    session.removeAttribute("role");
	    session.invalidate();
	    
	    return "redirect:/";
	}
}