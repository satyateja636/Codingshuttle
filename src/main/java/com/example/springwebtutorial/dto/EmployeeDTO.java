package com.example.springwebtutorial.dto;

import java.time.LocalDate;

import com.example.springwebtutorial.annotation.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class EmployeeDTO {

	private Long id;
	
	@NotNull(message = "Required field in Employee: namwe")
	@Size(min=3, max=10, message="Number of characters in the name should be in between 3 and 10")
	private String name;
	
	@NotBlank(message="Email cannot be blank")
	@Email(message="Email should be valid email")
	private String email;
	
	@Max(value = 80, message="age can't be greater than 80")
	@Min(value = 18, message ="age should be greater than 18")
	private Integer age;
	
	@NotBlank(message="Role of Employee can't be nulls")
//	@Pattern(regexp = "^(ADMIN|USER)$", message = "Role of Employee can be USER or ADMIN")
	@EmployeeRoleValidation
	private String role;
	
	@PastOrPresent(message="dateOfJoining field in Employee cannot be in the future")
	private LocalDate dateOfJoining;
	
	@AssertTrue(message="Employee should be active  ")
	@JsonProperty("isActive")
	private Boolean isActive;
	
	@NotNull(message="salary of the employee should not be null")
	@Positive(message="salary of the employee should be positive")
	@Digits(integer=6, fraction=2,message="The salary can be in the form  XXXXX.YY")
	@DecimalMax(value="100000.99")
	@DecimalMin(value="100.50")
	private Double salary;
	
	
	
	
	public EmployeeDTO() {
 
	}


	public EmployeeDTO(Long id, String name, String email, Integer age, LocalDate dateOfJoining, Boolean isActive, String role, Double salary) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.dateOfJoining = dateOfJoining;
		this.isActive = isActive;
		this.role = role;
		this.salary = salary;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	
	
}
