package com.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
	
	public static String process (HttpServletRequest request) {
		
		switch(request.getRequestURI()) {
		
		case "/IBJK-Project-One/login.view":
			return "partials/login.html";
			
		case "/IBJK-Project-One/register.view":
			return "partials/register.html";
			
		case "/IBJK-Project-One/home.view":
			return "partials/home.html";
			
		default:
			return "index.html";
		}
	}

}
