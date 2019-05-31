package com.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    
    public static String process (HttpServletRequest request) {


        switch(request.getRequestURI()) {
      
        
        case "/IBJK-Project-One/login.view":
            System.out.println("login.view called");
            return "partials/login.html";

        case "/IBJK-Project-One/dashboard.view":
            //System.out.println("home.view called");
            return "partials/dashboard.html";

        default:
            return "/index.html";
        }
    }
}