package com.ai.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ai.model.Course;
import com.ai.model.User;
@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {

	Optional<Course> findByCourseId(String courseId);

}
