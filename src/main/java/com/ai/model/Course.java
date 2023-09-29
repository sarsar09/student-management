 package com.ai.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
@Entity
@Table(name= "course")
public class Course implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;	
	@NotEmpty
	@Column(name= "courseId")
	private String courseId;	
	@NotEmpty
	@Column(name= "name")
	private String name;
	

	  public Course(int id, @NotEmpty String courseId, @NotEmpty String name, Set<Student> students) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.name = name;
		this.students = students;
	}

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	@ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
	  private Set<Student> students = new HashSet<>();

	public int getId() {
		return id;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
