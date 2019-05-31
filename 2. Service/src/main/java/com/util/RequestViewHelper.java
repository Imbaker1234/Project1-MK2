package com.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    
    public static String process (HttpServletRequest request) {
    	System.out.println("in RequestViewHelper process method");

        switch(request.getRequestURI()) {

        case "/IBJK-Project-One/login.view":
            System.out.println("login.view called");
            return "/index.html";

        case "/IBJK-Project-One/register.view":
            //System.out.println("register.view called");
            return "/index.html";

        case "/IBJK-Project-One/dashboard.view":
            //System.out.println("home.view called");
            return "/index.html";

        default:
            return "/index.html";
        }
    }
}