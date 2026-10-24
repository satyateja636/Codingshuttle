package com.example.springwebtutorial.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.springwebtutorial.dto.EmployeeDTO;
import com.example.springwebtutorial.entities.EmployeeEntity;
import com.example.springwebtutorial.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}
	

	public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
		EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
		return modelMapper.map(employeeEntity, EmployeeDTO.class);
	}
	
	public List<EmployeeDTO> getAllEmployees(){
		List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
		return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).collect(Collectors.toList());
	}
	
	public EmployeeDTO createNewEmployee(@RequestBody EmployeeEntity inputEmployee) {
		EmployeeEntity employeeEntity =  employeeRepository.save(inputEmployee);
		return modelMapper.map(employeeEntity, EmployeeDTO.class);
	}
}
