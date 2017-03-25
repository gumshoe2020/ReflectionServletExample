package com.github.gumshoe2020.servlet.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.gumshoe2020.errors.APImethodNotFoundException;
import com.github.gumshoe2020.errors.Errors;

/**
 * Abstract foundation for the application program interface.
 * Based on: http://www.javaworld.com/article/2076267/java-web-development/untangle-your-servlet-code-with-reflection.html
 */
public abstract class APIbase extends CoreServlet {
	private static final long serialVersionUID = 2130552252893014946L;
	private static final Class<?>[] sFormalArgs = {HttpServletRequest.class,HttpServletResponse.class};

	public APIbase(){
		super();
	}
	
	//-------------------------------------------------------------------------------------------------------------------
	// Responds to incoming HTTP requests
	//-------------------------------------------------------------------------------------------------------------------
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGetOrPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGetOrPost(request,response);
	}

	private final void doGetOrPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			dispatch(getAPImethod(this,getMethodName(request)), this, request, response);
		} catch (APImethodNotFoundException ex) {
			fLogger.error("\"" + getMethodName(request) + "\" does not have a corresponding method in any children of the APIbase.");
			handleAPImethodNotFound(request, response);
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	// Handles for APImethod not found exceptions
	//-------------------------------------------------------------------------------------------------------------------
	abstract protected void handleAPImethodNotFound(HttpServletRequest request, HttpServletResponse response);
	//-------------------------------------------------------------------------------------------------------------------
	// Generic default response method
	//-------------------------------------------------------------------------------------------------------------------
	abstract protected void defaultRootResponse(HttpServletRequest request, HttpServletResponse response);

	//-------------------------------------------------------------------------------------------------------------------
	// Passes action invocations to subsequent methods
	//-------------------------------------------------------------------------------------------------------------------
	private final void dispatch(Method m, Object target, HttpServletRequest request, HttpServletResponse response) throws APImethodNotFoundException {
		try {
			m.invoke(target,new Object[]{request,response});
		} catch (IllegalAccessException ex) {
			throw new APImethodNotFoundException("Couldn't access APImethod");
		} catch (InvocationTargetException ex) {
			throw new APImethodNotFoundException("Object doesn't have method");
		} catch (Exception e){
			fLogger.error(Errors.err2String(e));
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	// Gets the method name corresponding to methods in children of the API base from the requestURI
	//-------------------------------------------------------------------------------------------------------------------
	private final String getMethodName(HttpServletRequest request) {
		/* The code in here changes based on what API style you use.
		 * 
		 * This one will break if you do lots of extra URI pieces like:
		 *  /ReflectionServletExample/group/subgroup/region/api/method 		*/
		String[] uriPieces = request.getRequestURI().split("/");
		if(uriPieces.length <= 3) return "defaultRootResponse"; 
		else return uriPieces[3];
	}
	//-------------------------------------------------------------------------------------------------------------------
	// Gets the method from the child class of this APIbase class
	//-------------------------------------------------------------------------------------------------------------------
	private final Method getAPImethod(Object target, String methodName) 
			throws APImethodNotFoundException {
		try {
			return target.getClass().getMethod(methodName,sFormalArgs);
		}
		catch(NoSuchMethodException ex) {
			throw new APImethodNotFoundException("Couldn't get method.");
		}
	}
}