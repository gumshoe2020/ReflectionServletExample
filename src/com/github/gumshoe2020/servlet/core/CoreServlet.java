package com.github.gumshoe2020.servlet.core;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import com.github.gumshoe2020.config.ServerProperties;
import com.github.gumshoe2020.errors.Errors;
import com.github.gumshoe2020.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The CoreServlet serves as the central repository of all globally
 * accessible objects like Loggers, ServerProperties/Config, Error
 * handling, sessions, data sources, etc.
 * 
 * !! All non-private global objects MUST be thread safe. 
 */
public abstract class CoreServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2130552252893014946L;
    private static final String FS = File.separator;
    
    protected static Logger fLogger = new Logger();
    protected static ServerProperties SP;
    protected static Gson gson = new GsonBuilder().create();
    
    public CoreServlet(){
    	super();
    }
    
    /**
     * initializing global objects
     */
    public void init(ServletConfig config) {
    	try {
    		/*  servletWebRoot points to real path for: 
    		 *  	In Eclipse: /ReflectionServletExample/WebContent/ 
    		 *  	In Tomcat:  /webapps/ReflectionServletExample/   		*/
			String servletWebRoot = config.getServletContext().getRealPath("/");
			
			// currently set to write to: /ReflectionServletExample/WebContent/logs/ReflectionServletExample.log
			Path logDirectory = Paths.get(servletWebRoot+FS+"logs");
			String logFileName = "ReflectionServletExampleLogFile";
    		fLogger = new Logger()
    				.setLogPath(logDirectory)
    				.setLogFileName(logFileName);
    		
    		fLogger.debug("SP path: " + Paths.get(servletWebRoot+FS+"server.properties"));
    		
    		SP = new ServerProperties(Paths.get(servletWebRoot+FS+"server.properties"), fLogger);
    		
		} catch (Exception e) {
			fLogger.error(Errors.err2String(e));
		}
    }
}