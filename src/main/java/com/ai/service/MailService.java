package com.ai.service;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ai.model.User;

@Service
public class MailService {
	public String getRandom() {
		Random rnd=new Random();
		int number =rnd.nextInt(999999);
		return String.format("%06d", number);
	}
	
	@Autowired
	private JavaMailSender mailSender;
	
//	public boolean sendEmail (User user) {
//		boolean test = false;
//		String toEmail= user.getEmail();
//		System.out.println ("Preparing to send OTP");
//		
//		
//		try {
//			SimpleMailMessage message= new SimpleMailMessage();
//			
//			message.setTo(toEmail);
//			message.setSubject("User Email Vertification");
//			message.setText("Welcome to Ace Inspiration.Please use this code to verify your account : " + user.getCode()  );
//			mailSender.send(message);
//			System.out.print("OTP sent");
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//	}
//		return test;
//		
//		
//		
//		
//	}
	
	public boolean sendEmail (User user) {
		System.out.print("Preparing to send OTP");
		boolean test=false;
		
		String toEmail= user.getEmail();
		String fromEmail="minmratbhoneaung.etc@gmail.com";
		String password="yhwvqdxqzmybxqdy";
		
		try {
			Properties pr=new Properties();
			pr.setProperty("mail.smtp.host", "smtp.gmail.com");
			pr.setProperty("mail.smtp.port", "587");
			pr.setProperty("mail.smtp.auth", "true");
			pr.setProperty("mail.smtp.starttls.enable", "true");
			pr.put("mail.smtp.socketFactory.port", "587");
			pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			
			
			Session session=Session.getInstance(pr, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication (fromEmail,password);
				}
			});
			
			Message mess=new MimeMessage(session);
			mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			mess.setFrom(new InternetAddress(fromEmail));
			mess.setSubject("User Email Vertification");
			mess.setText("Welcome to Ace Inspiration.Please use this code to verify your account : " + user.getCode()  );
			Transport.send(mess);
			System.out.print("OTP sent");
			test=true;
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return test;
	}
}
	
	
	
	
	
	
	
	
