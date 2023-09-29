package com.ai.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ai.model.Course;
import com.ai.model.Student;
import com.ai.model.User;
import com.ai.service.CourseService;
import com.ai.service.StudentService;

@Component
@ComponentScan("com.ai")

@Controller
public class StudentController {

	@Autowired
	private StudentService studentdao;

	@Autowired
	private CourseService coursedao;

	@RequestMapping(value = "/studentList", method = RequestMethod.GET)
	public String displayView(ModelMap m, HttpSession session) {
		List<Student> list = studentdao.getAllStudents();
		m.addAttribute("studentLists", list);
		return "studentList";
	}

	@RequestMapping(value = "/studentRegister", method = RequestMethod.GET)
	public ModelAndView studentRegister(ModelMap m) {
		List<Course> courseLists = coursedao.getAllCourses();
		m.addAttribute("courseLists", courseLists);
		return new ModelAndView("addStudent", "sar", new Student());
	}

	@RequestMapping(value = "/createStudent", method = RequestMethod.POST)
	public String createStudent(@ModelAttribute("sar") @Validated Student student, BindingResult result,
			@RequestParam("courseId") String[] courseId, ModelMap model, HttpSession session,
			HttpServletRequest request, RedirectAttributes ra, MultipartFile file) {
		List<Student> users = studentdao.getAllStudents();
		Iterator<Student> itr = users.iterator();

		while (itr.hasNext()) {
			Student s = itr.next();

			if (student.getStudentId().equals(s.getStudentId())) {
				model.addAttribute("error", "Duplicate Student ID !");
				return "addStudent";
			}

		}

		if (result.hasErrors()) {
			List<Course> courseLists = coursedao.getAllCourses();
			model.addAttribute("courseLists", courseLists);
			model.addAttribute("error", "Invalid Student required");
			return "addStudent";
		}
//		 
		if (student.getPhone() == null) {
			List<Course> courseLists = coursedao.getAllCourses();
			model.addAttribute("courseLists", courseLists);
			model.addAttribute("error", "Please enter your phone number");
			return "addStudent";
		}

		if (student.getCourseId() == null) {
			List<Course> courseLists = coursedao.getAllCourses();
			model.addAttribute("courseLists", courseLists);
			model.addAttribute("error", "Please choose your course");
			return "addStudent";
		}

//		 try {
//		        if (file != null && !file.isEmpty()) {
//		            String uploadPath = session.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "image" + File.separator;
//		            String imageName = file.getOriginalFilename();
//		            String imagePath = "C:\\Users\\Sar Sar\\OJT\\SRJPA\\WebContent\\WEB-INF\\image";
//		            File imageFile = new File(imagePath, imageName);
//
//		            // Transfer the uploaded file to the specified directory
//		            file.transferTo(imageFile);
//
//		            // Set the file name (imageName) in the student object
//		            student.setPhoto(imageName);
//		        } else {
//		            model.addAttribute("error", "Photo Required");
//		            return "addStudent";
//		        }
//		    } catch (IOException e) {
//		        // Handle the exception gracefully, e.g., log it or show an error message
//		        model.addAttribute("error", "An error occurred while uploading the photo.");
//		        return "addStudent";
//	        }
//		 String imageData =  student.getPhoto();
		Set<Course> selectedCourses = new HashSet<>();
		for (String courseId1 : courseId) {
			Optional<Course> course = (Optional<Course>) coursedao.selectByCourseId(courseId1);
			if (course != null) {
				selectedCourses.add(course.get());
			}
		}

		System.out.println(student);
		Student dto = new Student();
		dto.setCourses(selectedCourses);
		dto.setId(student.getId());
		dto.setStudentId(student.getStudentId());
		dto.setName(student.getName());
		dto.setDob(student.getDob());
		dto.setGender(student.getGender());
		dto.setPhone(student.getPhone());
		dto.setEducation(student.getEducation());
		dto.setCourseId(student.getCourseId());
//		 dto.setPhoto(imageData);
		int rs = studentdao.save(dto);

		if (rs == 0) {
			List<Course> courseLists = coursedao.getAllCourses();
			model.addAttribute("courseLists", courseLists);
			model.addAttribute("error", "Insert Failed");
			request.setAttribute("registrationResult", "failed");
			return "addStudent";

		}

		request.setAttribute("registrationResult", "success");
		return "redirect:/studentList";

	}

//	  @RequestMapping(value="/updateStudent/{id}",method=RequestMethod.GET)
//		public ModelAndView updateStudent2(@PathVariable int id,ModelMap model,HttpSession session) {
//	        Student dto = new Student();         
//	        dto.setId(id);
//	        Optional<Student> student = studentdao.getStudentsByStudentId(id); 
//	        List<Course> courseLists = coursedao.getAllCourses();
//			model.addAttribute("courseLists",courseLists);			
//			
//			return new ModelAndView("updateStudent","bean",student);
//		}
//	  
//	
	@RequestMapping(value = "/updateStudent/{id}", method = RequestMethod.GET)
	public ModelAndView updateStudent2(@PathVariable int id, ModelMap model, HttpSession session) {
		Optional<Student> studentOptional = studentdao.getStudentsByStudentId(id);

		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();
			List<Course> courseLists = coursedao.getAllCourses();
			model.addAttribute("courseLists", courseLists);
			model.addAttribute("bean", student);

			// Create a Set<Integer> of enrolled course IDs
			Set<String> enrolledCourseIds = student.getCourses().stream().map(Course::getCourseId)
					.collect(Collectors.toSet());

			// Add it to the model
			model.addAttribute("enrolledCourseIds", enrolledCourseIds);
			return new ModelAndView("updateStudent");
		} else {
			// Handle the case where the student with the given id is not found
			// You can redirect to an error page or handle it as appropriate for your
			// application
			return new ModelAndView("errorPage");
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update2(@ModelAttribute("bean") @Validated Student student, BindingResult result, ModelMap model,
			@RequestParam("courseId")String[] courseIds,
			HttpSession session) {
		if (result.hasErrors()) {
			List<Course> courseLists = coursedao.getAllCourses();
			model.addAttribute("courseLists", courseLists);
			model.addAttribute("error", "Invalid Category required");
			return "updateStudent";
		}
//		         try {	
//				   		
//						MultipartFile image = student.getPhoto();
//						if (image == null || image.isEmpty()) {	
//							List<Course> courseLists = coursedao.selectAll();
//							model.addAttribute("courseLists",courseLists);
//							model.addAttribute("error", "Image Required");
//						    return "updateStudent";
//						}
//						byte[] bytes = image.getBytes();
//						
//						System.out.print(bytes.length);
//						if (bytes.length < 1) {		
//							List<Course> courseLists = coursedao.selectAll();
//							model.addAttribute("courseLists",courseLists);
//							model.addAttribute("error", "Image Required");
//						    return "updateStudent";
//						}			
//									 
//				
//						String uploadPath =session.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "image"
//								+ File.separator +  student.getPhoto().getOriginalFilename();
//
//						String imagePath = "C:\\Users\\Sar Sar\\OJT\\LSReg\\WebContent\\WEB-INF\\image";		
//						
//						File imageFile = new File(imagePath, image.getOriginalFilename());
//						image.transferTo(imageFile);
//
//						
//						BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(uploadPath ));
//						bout.write(bytes);
//						bout.close();
//					} catch (IOException e) {
//						System.out.print(e);
//					}
//			   		String imageData =  student.getPhoto().getOriginalFilename();

		Student dto = new Student();
		dto.setId(student.getId());
		System.out.println(student.getId());
		dto.setStudentId(student.getStudentId());
		dto.setName(student.getName());
		dto.setDob(student.getDob());
		dto.setGender(student.getGender());
		dto.setPhone(student.getPhone());
		dto.setEducation(student.getEducation());
		dto.setCourseId(student.getCourseId());
		studentdao.update(dto, courseIds);

		return "redirect:/studentList";

	}

//		  
//		  
//	  
	@RequestMapping(value = "deleteStudent/{id}", method = RequestMethod.GET)
	public String deleteStudent(@PathVariable("id") int id, ModelMap model) {
		Student dto = new Student();
		dto.setId(id);

		studentdao.delete(id);

		return "redirect:/studentList";
	}

//
	@RequestMapping(value = "/studentSearch", method = RequestMethod.GET)
	public String studentSearch(Model model, @RequestParam(value = "studentId", required = false) String studentId,
			@RequestParam(value = "name", required = false) String name) {
		List<Student> students = studentdao.searchStudent(studentId, name);
		model.addAttribute("studentLists", students);
		model.addAttribute("studentId", studentId);
		model.addAttribute("name", name);

		return "studentList";
	}
//	  
//	  

//	  @RequestMapping(value="/welcome",method=RequestMethod.GET)		
//	  public String welcomePage() {		   	   
//			   
//		  return "welcome";
//	}

}
