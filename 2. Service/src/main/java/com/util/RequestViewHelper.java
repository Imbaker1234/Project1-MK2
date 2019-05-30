package com.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    
    public static String process (HttpServletRequest request) {

        //String pathExtension = getPathExtension(request.getRequestURI());

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

    /** This method breaks down the URI so that it only retrieves the
     * relevant part at the end. After the project localhost and project
     * designation which are left out. So
     *
     * http://localhost:8080/IBJK_Project_One_war_exploded/login/request/blahblah
     *
     * becomes
     *
     * /login/request/blahblah
     *
     * @param requestURI
     * @return
     */
/*
    public static String getPathExtension(String requestURI) {
        String[] pathArr = requestURI.split("/");
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < pathArr.length; i++) {
            sb.append("/" + pathArr[i]);
        }
        return sb.toString();
    }
*/
}