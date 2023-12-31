package com.ai.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ai.model.ForgotPasswordToken;
import com.ai.repository.ForgotPasswordRepository;

@Service
public class ForgotPasswordService {
	@Autowired 
	ForgotPasswordRepository fpRepo;
	
	@Autowired
	JavaMailSender javaMailSender;
	private final int HOURS = 2;
	
	public String generateToken() {
		return UUID.randomUUID().toString();
		
		
	}
	
	public LocalDateTime expireTimeRange () {
		return LocalDateTime.now().plusHours(HOURS);
	}
	
	public void sendEmail(String to,String subject, String emailLink) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper (message);
		String emailContent = "<p>Hello</p>"
									+"Click the link to reset Password"
				+"<p><a href=\" " + emailLink + " \">Change My Password</a> </p>"+
									"<br>";
		helper.setText(emailContent, true);
		helper.setFrom("minmratbhoneaung.etc@gmail.com", "ACE Inspiration");
		helper.setSubject(subject);
		helper.setTo(to);
		javaMailSender.send(message);
		
	}
	
	public boolean isExpired  (ForgotPasswordToken fwToken) {
		return LocalDateTime.now().isAfter(fwToken.getExpireTime());
		
	}
	
	public String checkValidity(ForgotPasswordToken fwToken,Model model) {
		if (fwToken == null) {
			model.addAttribute("error", "Invalid Token");
			return "error";
		}
		else if (fwToken.isUsed()) {
			model.addAttribute("error", "The Token is already Used");
			return "error";
		}
		else if (isExpired(fwToken)) {
			model.addAttribute("error", "The Token is Expired");
			return "error";
			
		}else {
			return "resetPassword";
		}
		
		}
}