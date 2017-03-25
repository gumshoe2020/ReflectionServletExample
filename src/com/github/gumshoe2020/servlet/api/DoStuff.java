package com.github.gumshoe2020.servlet.api;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.gumshoe2020.errors.ErrorToken;
import com.github.gumshoe2020.errors.ErrorType;
import com.github.gumshoe2020.errors.Errors;
import com.github.gumshoe2020.servlet.core.APIbase;

/**
 * DoStuff is a worker class with APIbase as the parent.
 * As a result, it has full access to all 
 * of the CoreServlet's protected goodies.
 * 
 * The service methods in here need to be public, otherwise
 * the APIbase will throw an APImethodNotFoundException.
 */
public class DoStuff extends APIbase {
	private static final long serialVersionUID = 2130552252893014946L;

	public DoStuff() {
    	super();
    }

	@Override
	public void handleAPImethodNotFound(HttpServletRequest request, HttpServletResponse response) {
		try {
			ErrorToken et = Errors.tokenize(ErrorType.apiMethodNotFound);
			response.getWriter().println(et);
			response.setStatus(et.getHttpCode());
		} catch (Exception e) {
			fLogger.error(Errors.err2String(e));
		}
	}

	/**
	 * Default response method for the DoStuff root.
	 * 
	 * When running on Tomcat, the URL for this call is: 
	 * http://localhost:8080/ReflectionServletExample/doStuff
	 * or http://localhost:8080/ReflectionServletExample/doStuff/defaultRootResponse
	 */
	@Override
	public void defaultRootResponse(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try{
			out = response.getWriter();
			
			out.println("log file location: " + fLogger.getLogFilePath());
			out.println("remote IP: " + request.getRemoteAddr());
			out.println("request URI: " + request.getRequestURI());
			out.println("Server properties location: " + SP.getPath());
		} catch(Exception e){
			fLogger.error(Errors.err2String(e));
		}
	}
	
	/*
	 * Any other methods can be added below here with the URL:
	 * http://localhost:8080/ReflectionServletExample/doStuff/methodName
	 */
//	 public void methodName(HttpServletRequest request, HttpServletResponse response) {}

}