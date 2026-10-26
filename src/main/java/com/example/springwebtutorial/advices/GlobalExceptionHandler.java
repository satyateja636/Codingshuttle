package com.example.springwebtutorial.advices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springwebtutorial.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception) {

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());

		return buildErrorResponseEntity(apiError);
	}

	private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
		return buildErrorResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleInpuValidationErrors(MethodArgumentNotValidException exception) {
		List<String> errors = exception.getBindingResult().getAllErrors().stream()
				.map(error -> error.getDefaultMessage()).collect(Collectors.toList());

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
		return buildErrorResponseEntity(apiError);

	}
}