package com.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    
    public static String process (HttpServletRequest request) {

        switch(request.getRequestURI()) {

        case "/IBJK-Project-One/login.view":
            System.out.println("=====REQUEST VIEW HELPER=====");
            System.out.println("login.view called");
            return "partials/login.html";

        case "/IBJK-Project-One/register.view":
            System.out.println("=====REQUEST VIEW HELPER=====");
            System.out.println("register.view called");
            return "partials/register.html";

        case "/IBJK-Project-One/home.view":
            System.out.println("=====REQUEST VIEW HELPER=====");
            System.out.println("home.view called");
            return "partials/home.html";

        default:
            return "/index.html";
        }
    }
}