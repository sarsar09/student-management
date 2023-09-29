package com.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.model.Course;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course,Integer>{

}
