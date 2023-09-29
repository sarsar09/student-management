package com.ai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.model.Course;
import com.ai.model.User;
import com.ai.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	CourseRepository courseRepo;
	
	  public void save(Course courses) {
		    
			courseRepo.save(courses);
			
			} 
		   
		   public void update(Course courses,Integer id) 
			{ 
			courseRepo.save(courses);
			} 
		   
			public void delete(Integer id) 
			{ 
			courseRepo.deleteById(id);
			} 
			
			public List<Course> getAllCourses() 
			{ 
			List<Course> list = (List<Course>)courseRepo.findAll();
			return list;
			} 
			
			public Optional<Course> selectByCourseId(String courseId) {
		        return courseRepo.findByCourseId(courseId);
		    }
			
			

}
