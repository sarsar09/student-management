package com.ai.service;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.tomcat.jni.File;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ResourceUtils;
//
//import com.ai.model.Course;
//import com.ai.repository.CourseRepository;
//
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//@Service
//public class JReportService {
//	
//	@Autowired
//	private CourseRepository repository;
//	
//	public void exportJasperReport (HttpServletResponse response) throws JRException, IOException{
//	List <Course> courses = (List<Course>) repository.findAll();
//	java.io.File file=ResourceUtils.getFile("classpath:courseList.jrxml");
//	JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//	JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(courses);
//	Map<String,Object> parameters = new HashMap<>();
//	parameters.put("createdBy", "Simplifying Tech");
//	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
//	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//	}
//} 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat; // Add this import
import java.text.SimpleDateFormat; // Add this import
import java.util.Date; // Add this import
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.ai.model.Course;
import com.ai.model.Student;
import com.ai.model.User;
import com.ai.repository.CourseRepository;
import com.ai.repository.StuJRepository;
import com.ai.repository.UserJRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JReportService {

    @Autowired
    private CourseRepository repository;
    
    @Autowired
    private UserJRepository userRepo;
    
    @Autowired
    private StuJRepository stuRepo;

    public void exportJasperReport(HttpServletResponse response) throws JRException, IOException {
        List<Course> courses = (List<Course>) repository.findAll();
        File file = ResourceUtils.getFile("classpath:courseList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(courses);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Simplifying Tech");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
    
    public void exportUserReport(HttpServletResponse response) throws JRException, IOException {
        List<User> users = (List<User>) userRepo.findAll();
        File file = ResourceUtils.getFile("classpath:userList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Simplifying Tech");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
    
    public void exportStuReport(HttpServletResponse response) throws JRException, IOException {
        List<Student> students = (List<Student>) stuRepo.findAll();
        File file = ResourceUtils.getFile("classpath:stuList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Simplifying Tech");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
