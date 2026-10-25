package com.example.springwebtutorial.annotation;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation, String>{

	@Override
	public boolean isValid(String inputRole, ConstraintValidatorContext context) {
		List<String> roles = List.of("USER", "ADMIN");
		
		return roles.contains(inputRole);
	}

}
