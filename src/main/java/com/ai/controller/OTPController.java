 package com.ai.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ai.model.User;
import com.ai.service.MailService;
import com.ai.service.UserService;

@Controller
public class OTPController {
	@Autowired
	private MailService ms;
	
	@Autowired
	private UserService userService;
	
	
	

	
	@RequestMapping(value = "/UserVerify", method = RequestMethod.POST)
	public String sendEmail(@RequestParam String email, ModelMap model, HttpSession session) {
	    String code = ms.getRandom();
	    System.out.print(code);
	    User user = new User(email, code);
	    ms.sendEmail(user);

	    // Store the User object in the session with the key "user"
	    session.setAttribute("user", user);

	    return "verify";
	}
	
	 @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
	    public String verifyCode(HttpSession session, Model model, @RequestParam String code) {
	        User user = (User) session.getAttribute("user"); // Use "user" as the key

	        if (user != null && code.equals(user.getCode())) {
	        	
	           	
	        	 
	           	 return "redirect:/";
	        } else {
	            model.addAttribute("error", "Incorrect Verification code");
	            return "verify";
	        }
	    }


    @RequestMapping(value="/verify", method=RequestMethod.GET)
    public String verifyEmail(Model model) {
    	model.addAttribute("user", new User());
        return "verify"; 
    }
	

    
  
    
   



        
        
    }



