package com.ai.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ai.model.Student;
import com.ai.model.User;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> 
{

	List<Student> findByStudentIdContainingAndNameContaining(String studentId, String name);
	
	}
