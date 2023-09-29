package com.ai.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ai.model.User;
//repository that extends CrudRepository 
@Repository
public interface UserRepository extends CrudRepository<User, Integer> 
{
	User findByNameAndPassword(String name, String password);
	User findByEmail(String email);

	List<User> findByUserIdContainingAndNameContaining(String userId, String name);
	}

