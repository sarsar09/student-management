package com.ai.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ai.model.Login;
import com.ai.model.User;
import com.ai.service.UserService;

@ComponentScan("com.ai")
@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/")
	
	public ModelAndView userLogin() {
		return new ModelAndView("login","bean",new Login());
	}
	
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public String loginProcess(@ModelAttribute("bean") @Validated Login login, BindingResult result, ModelMap model, HttpSession session) {
	    if (result.hasErrors()) {
	        model.addAttribute("error", "Please fill in the required fields.");
	        return "login";
	    }

	    User userdto = userService.getLoginUser(login);

	    if (userdto == null) {
	        model.addAttribute("error", "Invalid name or password!");
	        return "login";
	    }

	    String role = userdto.getRole();

	    if (role == null) {
	        model.addAttribute("error", "You haven't registered ");
	        return "login";
	    }

	    if (!login.getPassword().equals(userdto.getPassword())) {
	        model.addAttribute("error", "Your password is wrong!");
	        return "login";
	    }

	    session.setAttribute("userId", userdto.getUserId());
	    session.setAttribute("loggedin", true);
	    session.setAttribute("username", login.getName());
	    session.setAttribute("ROLE", role);

	    if (role.equals("admin")) {
	        return "welcome";
	    } else {
	        return "welcome";
	    }
	}
}
