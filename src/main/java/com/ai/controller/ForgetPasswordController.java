package com.ai.controller;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ai.model.ForgotPasswordToken;
import com.ai.model.Login;
import com.ai.model.Student;
import com.ai.model.User;
import com.ai.repository.ForgotPasswordRepository;
import com.ai.service.ForgotPasswordService;
import com.ai.service.MailService;
import com.ai.service.UserService;
@Component
@ComponentScan("com.ai")
@Controller
public class ForgetPasswordController {

	@Autowired
	private UserService userService;
	
	@Autowired	
	private ForgotPasswordService fwService;
	
	@Autowired
	private ForgotPasswordRepository fwRepo;
	
	
	
	@GetMapping("/password-request")
	public String passwordRequest() {
		return "password-request";
	}
	
	@PostMapping("/password-request")
	public String savePasswordRequest(@RequestParam("email") String  email,Model model,Login login) {
		User user=userService.getEmail(login);
		if(user== null) {
			model.addAttribute("error", "This Email is not Registered");
			return "password-request";
		}
		
		ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
		forgotPasswordToken.setExpireTime(fwService.expireTimeRange());
		forgotPasswordToken.setToken(fwService.generateToken());
		forgotPasswordToken.setUser(user);
		forgotPasswordToken.setUsed(false);
		fwRepo.save(forgotPasswordToken);
		String emailLink ="http://localhost:8080/resetPassword?token=" + forgotPasswordToken.getToken();
		try {
			fwService.sendEmail(user.getEmail(), "Password Reset Link", emailLink);
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error sending Email");
			return "password-request";
		}
		return "redirect:/password-request?success";
	}
	
	@GetMapping("/resetPassword")
	public String resetPassword(@Param(value= "token") String token,Model model,HttpSession session) {
		session.setAttribute( "token", token);
		ForgotPasswordToken fpToken=fwRepo.findByToken(token);
		 return fwService.checkValidity(fpToken, model);
		
	}
	
	@PostMapping("/resetPassword")
	public String savePassword(HttpServletRequest request,HttpSession session,Model model) {
		String password = request.getParameter("password");
		String token=(String) session.getAttribute("token");
		ForgotPasswordToken fwToken=fwRepo.findByToken(token);
		User user=fwToken.getUser();
		user.setPassword(password);
		fwToken.setUsed(true);
		 userService.save(user);
		 fwRepo.save(fwToken);
		 model.addAttribute("msg", "Password Reset is successful!");
		 return "success";
		 
		 
		
	}
	
	
	

	
	


	

	
}
