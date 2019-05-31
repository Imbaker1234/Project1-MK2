package com.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    
    public static String process (HttpServletRequest request) {


        switch(request.getRequestURI()) {
      
        
        case "/IBJK-Project-One/login.view":
            return "partials/login.html";

        case "/IBJK-Project-One/dashboard.view":
            return "partials/dashboard.html";
            
        case "/IBJK-Project-One/pasttickets.view":
            return "partials/pasttickets.html";

        default:
            return "/index.html";
        }
    }
}