package com.example.springwebtutorial.advices;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiResponse<T> {
	
	@JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
	private LocalDateTime timeStamp;
	
	private T data;
	
	private ApiError apiError;
	
	public ApiResponse() {
		this.timeStamp = LocalDateTime.now();
		
	}
	
	public ApiResponse(T data) {
		this();
		this.data = data;
	}

	public ApiResponse(ApiError error) {
		this();
		this.apiError = error;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ApiError getApiError() {
		return apiError;
	}

	public void setApiError(ApiError apiError) {
		this.apiError = apiError;
	}
	
	
}
