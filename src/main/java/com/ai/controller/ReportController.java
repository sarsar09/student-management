package com.ai.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ai.model.Course;
import com.ai.model.Student;
import com.ai.model.User;
import com.ai.repository.CourseJpaRepository;
import com.ai.repository.StuJRepository;
import com.ai.repository.UserJRepository;
import com.ai.service.JReportService;

import net.sf.jasperreports.engine.JRException;
@ComponentScan("com.ai")
@Controller
public class ReportController {
	
	@Autowired
	private CourseJpaRepository repo;
	
	@Autowired
	private UserJRepository userRepo;
	
	@Autowired
	private StuJRepository stuRepo;
	
	@Autowired
	JReportService service;
	
	@GetMapping("/getCourse")
	public List<Course>getCourse(){
		List<Course>course=(List<Course>)repo.findAll();
		return course;
	}
	
	@GetMapping("/getUser")
	public List<User>getUser(){
		List<User>user=(List<User>)userRepo.findAll();
		return user;
	}
	
	@GetMapping("/getStu")
	public List<Student>getStu(){
		List<Student>stu=(List<Student>)stuRepo.findAll();
		return stu;
	}

 	@GetMapping("/exportCourse")
	public void createPDF(HttpServletResponse response)throws IOException,JRException{
		response.setContentType( "application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date ());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;  filename=CourseList_" + currentDateTime +".pdf";
		response.setHeader(headerKey, headerValue);
		service.exportJasperReport (response);
		 
	}
 	
 	@GetMapping("/exportUser")
	public void createUser(HttpServletResponse response)throws IOException,JRException{
		response.setContentType( "application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date ());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;  filename=UserList_" + currentDateTime +".pdf";
		response.setHeader(headerKey, headerValue);
		service.exportUserReport(response);
		 
	}
 	
 	@GetMapping("/exportStu")
	public void createStu(HttpServletResponse response)throws IOException,JRException{
		response.setContentType( "application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date ());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;  filename=StudentList_" + currentDateTime +".pdf";
		response.setHeader(headerKey, headerValue);
		service.exportStuReport(response);
		 
	}
 	


}
