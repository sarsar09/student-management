package com.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.model.Course;
import com.ai.model.Student;
@Repository
public interface StuJRepository extends  JpaRepository<Student,Integer> {

}
