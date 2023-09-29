package com.ai;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.model.Course;
import com.ai.repository.CourseJpaRepository;
import com.ai.service.JReportService;

import net.sf.jasperreports.engine.JRException;

@SpringBootApplication
@RestController
public class StuRegSpringBootApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(StuRegSpringBootApplication.class, args);
	}

}
