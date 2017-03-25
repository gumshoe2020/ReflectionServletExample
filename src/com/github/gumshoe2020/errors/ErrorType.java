package com.github.gumshoe2020.errors;

public enum ErrorType {
	
	database(500),
	server(500),
	usernameOrEmailAlreadyRegistered(500),
	nullInputs(400),
	malformedUrl(400),
	invalidInputs(400),
	unauthorized(401),
	temporarilyBanned(401),
	emailNotVerified(401),
	usernameNotFound(401),
	forbidden(403),
	notFound(404),
	apiMethodNotFound(404),
	viewNotFound(404),
	maxLoginAttempts(401),
	failedLoginAttempt(401)
	;
	
	private int httpStatus;
	
	private ErrorType(int httpSt){
		this.httpStatus = httpSt;
	}
	
	public int getHttpStatus(){
		return httpStatus;
	}
}
