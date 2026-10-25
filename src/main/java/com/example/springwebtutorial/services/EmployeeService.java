package com.example.springwebtutorial.services;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.springwebtutorial.dto.EmployeeDTO;
import com.example.springwebtutorial.entities.EmployeeEntity;
import com.example.springwebtutorial.exceptions.ResourceNotFoundException;
import com.example.springwebtutorial.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}
	

	public Optional<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id) {
//		Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
//		return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity, EmployeeDTO.class));
		
		return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
	}
	
	public List<EmployeeDTO> getAllEmployees(){
		List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
		return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).collect(Collectors.toList());
	}
	
	public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee) {
		EmployeeEntity employeeEntity =  employeeRepository.save(modelMapper.map(inputEmployee, EmployeeEntity.class));
		return modelMapper.map(employeeEntity, EmployeeDTO.class);
	}


	public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
		boolean exists = isExistsByEmployeeId(employeeId);
		if(!exists) throw new ResourceNotFoundException("Employee not found with id :"+employeeId);
		EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
		employeeEntity.setId(employeeId);
		EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
		
		return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
	}


	public boolean deleteEmployeeById(Long employeeId) {
		boolean exists = isExistsByEmployeeId(employeeId);
		if(!exists) return false;
		employeeRepository.deleteById(employeeId);
		return true;
	}


	public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
		boolean exists = isExistsByEmployeeId(employeeId);
		if(!exists)return null;
		EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
		if (employeeEntity == null) {
	        return null;
	    }
//		updates.forEach((field, value) -> 
//			{Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);
//			fieldToBeUpdated.setAccessible(true);
//			ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
//			});
		
		updates.forEach((key, value) -> {
		    try {
		        setFieldValue(employeeEntity, key, value);
		    } catch (ReflectiveOperationException e) {
		        throw new RuntimeException("Unable to update field: " + key, e);
		    }
		});
		return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
		
		
	}
	
	public boolean isExistsByEmployeeId(Long employeeId) {
		return employeeRepository.existsById(employeeId);
	}
	
	 private void setFieldValue(
	            EmployeeEntity employee,
	            String fieldName,
	            Object value) throws ReflectiveOperationException {

	        Field field = EmployeeEntity.class.getDeclaredField(fieldName);
	        field.setAccessible(true);

	        Class<?> fieldType = field.getType();
	        Object convertedValue = value;

	        if (value != null) {
	            if (fieldType == Integer.class) {
	                convertedValue = Integer.valueOf(value.toString());
	            } else if (fieldType == Long.class) {
	                convertedValue = Long.valueOf(value.toString());
	            } else if (fieldType == Boolean.class) {
	                convertedValue = Boolean.valueOf(value.toString());
	            } else if (fieldType == LocalDate.class) {
	                convertedValue = LocalDate.parse(value.toString());
	            } else if (fieldType == String.class) {
	                convertedValue = value.toString();
	            } else {
	                throw new IllegalArgumentException(
	                        "Unsupported field type: " + fieldType.getName());
	            }
	        }

	        field.set(employee, convertedValue);
	    }
}
