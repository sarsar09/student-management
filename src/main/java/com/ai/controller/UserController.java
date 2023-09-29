package com.ai.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ai.model.Login;
import com.ai.model.User;
import com.ai.service.UserService;
@Component
@ComponentScan("com.ai")
@Controller
public class UserController {

	
	@Autowired
	private UserService userdao;
	
	@ModelAttribute("bean")
	public User getUser() {
		return new User();
	}
	
	@RequestMapping(value="/userList",method=RequestMethod.GET)
	public String displayView(ModelMap m) {
		List<User> list = userdao.getAllUsers();
		m.addAttribute("userLists", list);
		return "userList";
	}
	

    @GetMapping("/userRegister")
 	public ModelAndView userRegister() {
 		return new ModelAndView("register","bean",new User());		
 		
 	} 
    
    
    @RequestMapping(value="/addUser" ,method=RequestMethod.POST)
    public String addUser(@ModelAttribute("bean") @Validated User user, BindingResult result, ModelMap model,HttpSession session) {
		
   	 if(result.hasErrors()) {
			model.addAttribute("error","Invalid User required");
			return "register";
		}
   	List<User> users = userdao.getAllUsers();
   	Iterator<User> itr=users.iterator();
    
    while(itr.hasNext()) {
    	User use=itr.next();
    	
   	if(user.getEmail().equals(use.getEmail())) {
		model.addAttribute("error","Your email already exists !");
		return "register";
   	}
   	if(!user.getEmail().endsWith("@gmail.com")) {
		model.addAttribute("error","Email Should Contain @gmail.com");
		return "register";
	}
   	}
   	
   	if (!user.getPassword().matches(".*[!@#$%^&*].*")) {
	    model.addAttribute("error", "Password must contain a special character!");
	    return "register";
	} else if (!user.getPassword().matches(".*\\d.*")) {
	  
	    model.addAttribute("error", "Password must contain a number!");
	    return "register";
	} else if (!user.getPassword().matches(".*[A-Z].*")) {
	    model.addAttribute("error", "Password must contain an uppercase letter!");
	    return "register";
	}  else 	if(!user.getPassword().equals(user.getConfirmPassword())) {
    	model.addAttribute("error","Password didn't match");
        return "register";
    }
   	
   	
   
   	 
   	 
   	User dto = new User();
   	 dto.getId();
   	 dto.setUserId(user.getUserId());
   	dto.setName(user.getName());
  	 dto.setEmail(user.getEmail());
  	 dto.setPassword(user.getPassword());
  	 dto.setConfirmPassword(user.getConfirmPassword());
  	 dto.setRole(user.getRole());
  	 int rs = userdao.save(dto);
  	 
  	 if(rs == 0) {
  		 model.addAttribute("error","Insert Failed");
  		return "addUser";
  	 }
   	
   	 
   	 session.setAttribute("userId",dto.getUserId());
   	 System.out.println(dto.getUserId()+"eee");
   	return "forward:/UserVerify";
   	 
    }
    
   
    
    @RequestMapping(value="/updateUser/{id}",method=RequestMethod.GET)
    public ModelAndView updateUser( @PathVariable int id  ) {
    	User dto = new User();
    	dto.setId(id);
    	Optional<User> user = userdao.getUsersByUserId(id);
    	ModelAndView m=new ModelAndView("updateUser");
    	m.addObject("bean",user);
    	return m;
    }
//    
    @RequestMapping(value="/updateUser",method=RequestMethod.POST)
    public String updateUser(@ModelAttribute("bean") @Validated User user,BindingResult result,ModelMap model,HttpSession session) {
    	if(result.hasErrors()) {
    		model.addAttribute("error","Invalid user update required");
    		return "updateUser";
    	}
    	
    	User dto = new User();
    	dto.setId(user.getId());
    	 dto.setUserId(user.getUserId());
       	 dto.setName(user.getName());
    	dto.setEmail(user.getEmail());
    	dto.setPassword(user.getPassword());
    	dto.setConfirmPassword(user.getConfirmPassword());
    	dto.setRole(user.getRole());
    	userdao.update(dto, dto.getId());
    	
    	
    	
     	 return "redirect:/userList";
    }
//////    
    @RequestMapping(value="deleteUser/{id}",method=RequestMethod.GET)
    public String deleteUser(@PathVariable("id")int id,ModelMap model) {
    	User dto = new User();
    	dto.setId(id);
    	
    	userdao.delete(id);
    	
    	
    	return "redirect:/userList";
    }
//    
//    
////    
    @RequestMapping(value="/userSearch", method=RequestMethod.GET) 
	  public String studentSearch(Model model,
			  @RequestParam(value = "userId", required = false) String userId,
			  @RequestParam(value = "name", required = false) String name) {
	                              
	    
	      List<User> users = userdao.searchUser(userId, name);
	      model.addAttribute("userLists",users);
	      model.addAttribute("userId",userId);
	      model.addAttribute("username",name);

	      return "userList";
	  }
//	  
	 
		
    
    
}
