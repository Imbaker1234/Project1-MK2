package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    private static Logger log = LogManager.getLogger(RequestViewHelper.class);

    public static String process(HttpServletRequest request) {

        log.info("RequestViewHelper: Process() called\n" + request.getRequestURI() + " was the URI provided.\n"); //DEBUG
        switch (request.getRequestURI()) {


            case "/IBJK-Project-One/login.view":
            case "/IBJK_Project_One/login.view":
                return "partials/login.html";
<<<<<<< HEAD
=======
                
>>>>>>> 3d15da6506159deffc83d216b357add4cd1450ae
            case "/IBJK_Project_One_/dashboard.view":
            case "/IBJK-Project-One/dashboard.view":
                return "partials/dashboard.html";

            case "/IBJK-Project-One/pasttickets.view":
            case "/IBJK_Project_One/pasttickets.view":
                return "partials/pasttickets.html";

            default:
                log.info("RequestViewHelper called the default in switch case");
                return "/partials/login.html";
        }
    }
}