package com.github.gumshoe2020.test;

import com.google.gson.Gson;

public class GenericTest {

	static Gson gson = new Gson();
	
	public static void main(String[] args) {
		method01();
	}
	
	public static void method01(){
		String s0 = "/ReflectionServletExample/doStuff/methodName/";
		String s1 = "/ReflectionServletExample/doStuff";
		
		String[] sts0 = s0.split("/");
		String[] sts1 = s1.split("/");
		
		System.out.println("sts0: " + gson.toJson(sts0) + " sts1: " + gson.toJson(sts1) +
				System.lineSeparator() + "sts0.length >? 3 : " +(sts0.length>3) +
				System.lineSeparator() + "sts1.length >? 3 : " +(sts1.length>3));
	}
}
