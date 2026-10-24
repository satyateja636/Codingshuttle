package com.example.springwebtutorial.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springwebtutorial.dto.EmployeeDTO;
//import com.example.springwebtutorial.dto.EmployeeDTO;
import com.example.springwebtutorial.entities.EmployeeEntity;
import com.example.springwebtutorial.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	
	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping(path = "/getSecretMessage")
	public String getMySuperSeceretMessage() {
		return "Secret Message: asdfr3@&hhd";
	}
	
//	@GetMapping("/employees/{employeeId}")
//	public EmployeeDTO getEmployee(@PathVariable Long employeeId) {
//		return new EmployeeDTO(employeeId, "Satya", "satyateja636@gmail.com", 27, LocalDate.of(2026, 06, 29), true);
//	}
	
	
	@GetMapping("/{employeeId}")
	public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
		return employeeService.getEmployeeById(id);
	}
	  
	@GetMapping
	public List<EmployeeDTO> getAllEmployees(){
		return employeeService.getAllEmployees()  ;
	}
	
	@PostMapping
	public EmployeeDTO createNewEmployee(@RequestBody EmployeeEntity inputEmployee) {
		return employeeService.createNewEmployee(inputEmployee);
	}
	
//	@PostMapping
//	public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee) {
//		inputEmployee.setId(100L);
//		return inputEmployee;
//	}
	
	@GetMapping(path = "/employees")
	public String getAllEmployee(@RequestParam Integer age, 
			@RequestParam(required = false ) String sortBy){
		return "Hi age "+age+" "+sortBy;
	}
	
}
