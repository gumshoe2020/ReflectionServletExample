package com.github.gumshoe2020.errors;

import com.github.gumshoe2020.servlet.core.CoreServlet;

public class Errors extends CoreServlet {
	private static final long serialVersionUID = -389752536598055519L;
	//-------------------------------------------------------------------------------------------------------------------
	// Generic Log File Error Structruing
	//-------------------------------------------------------------------------------------------------------------------
	public static String err2String(Exception e){
		StringBuilder sb = new StringBuilder(System.lineSeparator());
		sb.append("\t\t").append(e.getCause() + ", " + e.getClass().getName()+ ": " + e.getMessage()).append(System.lineSeparator());
		for(int i=0; i<e.getStackTrace().length;i++)sb.append("\t\t").append(e.getStackTrace()[i].toString()).append(System.lineSeparator());
		return sb.toString();
	}
	//-------------------------------------------------------------------------------------------------------------------
	// Takes in ErrorType enum and processes corresponding error message from server.properties
	//-------------------------------------------------------------------------------------------------------------------
	public static ErrorToken tokenize(ErrorType type){
		ErrorToken et = gson.fromJson(SP.getProp("error."+type.toString()), ErrorToken.class);
		et.setHttpCode(type.getHttpStatus());
		return et;
	}
}