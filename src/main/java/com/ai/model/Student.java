package com.ai.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Entity
@Table(name= "student")
public class Student implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	@NotEmpty
	@Column(name= "studentId")
	private String studentId;
	@NotEmpty
	@Column(name= "name")
	private String name;
	@NotEmpty
	@Column(name= "dob")
	private String dob;
	@NotEmpty
	@Column(name= "gender")
	private String gender;
	@NotEmpty
	@Column(name= "phone")
	private String phone;
	@Column(name= "education")
	private String education;

	private String courseId;
	@Transient
	private String courseName;
	 @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	  @JoinTable(name="stud_course", joinColumns = {@JoinColumn(name = "studentId")},
	             inverseJoinColumns = {@JoinColumn(name = "courseId")})
	  private Set<Course> courses = new HashSet<>(); 



	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}




	public Student() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	
//	public MultipartFile getPhoto() {
//		return photo;
//	}
//
//	public void setPhoto(MultipartFile photo) {
//		this.photo = photo;
//	}

	public String getCourseId() {
		return courseId;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", studentId=" + studentId + ", name=" + name + ", dob=" + dob + ", gender="
				+ gender + ", phone=" + phone + ", education=" + education + ", courseId=" + courseId + "]";
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	


	
	


}
