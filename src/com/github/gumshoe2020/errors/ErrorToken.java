package com.github.gumshoe2020.errors;

import com.google.gson.Gson;

public class ErrorToken {

	private final static transient Gson gson = new Gson();
	
	private final String status = "error";
	private String errorType;
	private String errorMsg;
	private transient int httpCode = 500;
	private transient String redirectUrl = "/";
	
	@Override
	public String toString(){
		return gson.toJson(this);
	}
	
	public final String getErrorType() {
		return errorType;
	}
	public final void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public final String getErrorMsg() {
		return errorMsg;
	}
	public final void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public final int getHttpCode() {
		return httpCode;
	}
	public final void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}
	public final String getStatus() {
		return status;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}