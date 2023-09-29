package com.ai.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ai.model.Course;
import com.ai.model.User;
import com.ai.repository.CourseRepository;
import com.ai.service.CourseService;
@Component
@ComponentScan("com.ai")
@Controller
public class CourseController {

	@Autowired
	private CourseService dao;
	
	@RequestMapping(value="/courseList",method=RequestMethod.GET)
	public String displayView(ModelMap m) {
		List<Course> list = dao.getAllCourses();
		m.addAttribute("courseLists", list);
		return "courseList";
	}
	
	@RequestMapping(value="/createCourse",method=RequestMethod.GET)
 	public ModelAndView setupaddCategory() {
 		return new ModelAndView("addCourse","bean",new Course());		
 		
 	}
	
	  @RequestMapping(value="/addCourse" ,method=RequestMethod.POST)
	     public String addCategory(@ModelAttribute("bean") @Validated Course course, BindingResult result, ModelMap model) {
			
	    	 if(result.hasErrors()) {
	 			model.addAttribute("error","Invalid Category required");
	 			return "addCourse";
	 		}
	    	 
	    	 List<Course> users = dao.getAllCourses();
	    	   	Iterator<Course> itr= users.iterator();
	    	    
	    	    while(itr.hasNext()) {
	    	    	Course c=itr.next();
	    	    	
	    	    	if(course.getCourseId() == null){
	    			    	model.addAttribute("error","CourseId can't be blank!");		    
	    			    	return "addCourse";
	    				}	
	    	    	if(course.getName() == null){
    			    	model.addAttribute("error","Enter Course Name");		    
    			    	return "addCourse";
    				}	
	    	    	
	    	   	if(course.getName().equals(c.getName())) {
	    			model.addAttribute("error","This Course is already registered !");
	    			return "addCourse";
	    	   	}
	    	   	if(course.getCourseId().equals(c.getCourseId())) {
	    			model.addAttribute("error","Duplicate Course ID !");
	    			return "addCourse";
	    	   	}
	    	   	
	    	   	}
	    	
	    	 
	    	 Course dto = new Course();
	    	 dto.setId(course.getId());
	    	 dto.setCourseId(course.getCourseId());
	    	 dto.setName(course.getName());	    	
	    	 dao.save(dto);
	    	 
	    	
	    	 
	    	 return "addCourse";
	    	 
	     }
	  
	  @RequestMapping(value="deleteCourse/{id}",method=RequestMethod.GET)
	    public String deleteCourse(@PathVariable("id")int id,ModelMap model) {
	    	Course dto = new Course();
	    	dto.setId(id);
	    	
	    	dao.delete(id);
	    	
	    	
	    	return "redirect:/courseList";
	    }
}
