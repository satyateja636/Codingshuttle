package com.example.springwebtutorial.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<Optional<EmployeeDTO>> getEmployeeById(@PathVariable(name = "employeeId") Long id) {
		Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
		if(employeeDTO == null) return ResponseEntity.notFound().build();
		return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO)).orElse(ResponseEntity.notFound().build());
	}
	  
	@GetMapping
	public List<EmployeeDTO> getAllEmployees(){
		return employeeService.getAllEmployees()  ;
	}
	
	@PostMapping
	public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeEntity inputEmployee) {
		EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
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
	
	@PutMapping(path = "/{employeeId}")
	public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
		return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
	}
	
	@DeleteMapping(path = "/{employeeId}")
	public ResponseEntity<Boolean>  deleteEmployeeById(@PathVariable Long employeeId) {
		boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
		if(gotDeleted) return ResponseEntity.ok(true);
		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping(path = "/{employeeId}")
	public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId) {
		EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId, updates);
		if(employeeDTO == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(employeeDTO);
	}
}
