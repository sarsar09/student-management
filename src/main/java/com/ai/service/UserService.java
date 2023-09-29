package com.ai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.model.Login;
import com.ai.model.User;
import com.ai.repository.UserRepository;

@Service
public class UserService {
	
@Autowired
UserRepository userRepo;

public List<User> getAllUsers() 
{ 
List<User> list = (List<User>)userRepo.findAll();
return list;
} 

public Optional<User> getUsersByUserId(Integer id) {
return userRepo.findById(id);

}


   public int save(User users) {
     
	userRepo.save(users);
	return 1;
	} 
   
   public void update(User user,Integer id) 
	{ 
	userRepo.save(user);
	} 
   
   
	//deleting a specific record by using the method deleteById() 
	
	public void delete(Integer id) 
	{ 
	userRepo.deleteById(id);
	} 
	
	public User getLoginUser(Login login) {
        return userRepo.findByNameAndPassword(login.getName(), login.getPassword());
    }
	
	public User getEmail(Login login) {
		return userRepo.findByEmail(login.getEmail());
	}
	
	  public List<User> searchUser(String userId, String name) {
	        return userRepo.findByUserIdContainingAndNameContaining(userId, name);
	    }

	public void update(User resetUser) {
		userRepo.save(resetUser);
		
	}

	
	  
	
	
	
	
	

}
