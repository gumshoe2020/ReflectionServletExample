package com.github.gumshoe2020.errors;

import javax.servlet.ServletException;

public class APImethodNotFoundException extends ServletException {
	private static final long serialVersionUID = 7010575075215547497L;
	public APImethodNotFoundException(String string) {
		super(string);
	}
}
