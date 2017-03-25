package com.github.gumshoe2020.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

import com.github.gumshoe2020.logging.Logger;

public class ServerProperties {

	private Properties config = new Properties();
	private static Path propertiesFilePath;
	private final Logger log;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private final static String NL = System.lineSeparator();
	
	public ServerProperties(Path propsFilePath, Logger lgr) throws IOException{
		ServerProperties.propertiesFilePath = propsFilePath;
		this.log = lgr;
		try {
			Path path = propsFilePath;
			config.load(new ByteArrayInputStream(read(path).getBytes(ENCODING)));
		} catch (IOException e) {
			lgr.critical(String.format("%s%s", "Unable to load configuration file from ",propsFilePath.toString()));
		}
	}
	
	public synchronized void reloadProperties(){
		try {
			Path path = propertiesFilePath;
			config.load(new ByteArrayInputStream(read(path).getBytes(ENCODING)));
		} catch (IOException e) {
			log.critical(String.format("%s%s", "Unable to reload configuration file from ",propertiesFilePath.toString()));
		}
	}
	
	public synchronized void update(Path propsFilePath) throws IOException{
		File f = propsFilePath.toFile();
        OutputStream out = new FileOutputStream( f );
        config.store(out, "===== Updated by com.fate.common.objects.Config.java. =====");
	}
	
	public synchronized String getPath(){
		return propertiesFilePath.toString();
	}
	
	private synchronized String read(Path aPath) throws IOException {
		List<String> lines = Files.readAllLines(aPath, ENCODING);
		StringBuilder sb = new StringBuilder("");
		for(String line : lines){
			sb.append(line + NL);
		}
		return sb.toString();
	}
		
	public synchronized String getProp(String key){
		return config.getProperty(key);
	}
	
}
