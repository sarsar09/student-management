package com.ai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.model.Course;
import com.ai.model.Student;
import com.ai.model.User;
import com.ai.repository.CourseRepository;
import com.ai.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository stuRepo;
	@Autowired
	CourseRepository courseRepo;
	
	   public int save(Student stu) {
		     
			stuRepo.save(stu);
			return 1;
			} 
	   
	   public List<Student> getAllStudents() 
	   { 
	   List<Student> list = (List<Student>)stuRepo.findAll();
	   return list;
	   } 
	   
	   public Optional<Student> getStudentsByStudentId(Integer id) {
		   return stuRepo.findById(id);

		   }
	   
	   public void update(Student stu, String[] courseIds) {
	        // Step 1: Fetch the student entity by its ID
	        Student existingStudent = stuRepo.findById(stu.getId()).orElse(null);
	        
	        if (existingStudent != null) {
	            // Step 2: Update the student entity with new data
	            existingStudent.setName(stu.getName());
	            existingStudent.setDob(stu.getDob());
	            existingStudent.setEducation(stu.getEducation());
	            existingStudent.setGender(stu.getGender());
	            existingStudent.setPhone(stu.getPhone());
	            existingStudent.setStudentId(stu.getStudentId());
	            
	            // Update other attributes as needed
	            
	            // Step 3: Update the list of courses associated with the student
	            if (courseIds != null) {
	                // Clear the existing courses
	                existingStudent.getCourses().clear();
	                
	                // Add the selected courses
	                for (String courseId : courseIds) {
	                    Course course = courseRepo.findByCourseId(courseId).orElse(null);
	                    if (course != null) {
	                        existingStudent.getCourses().add(course);
	                    }
	                }
	            }
	            
	            // Save the updated student entity
	            stuRepo.save(existingStudent);
	        }
	    }
	   
	   public void delete(Integer id) 
		{ 
		stuRepo.deleteById(id);
		} 
	   
	   public List<Student> searchStudent(String studentId, String name) {
	        return stuRepo.findByStudentIdContainingAndNameContaining(studentId, name);
	    }
}
