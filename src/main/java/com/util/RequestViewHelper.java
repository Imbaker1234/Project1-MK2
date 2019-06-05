package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    private static Logger log = LogManager.getLogger(RequestViewHelper.class);

    public static String process(HttpServletRequest request) {

        log.info("RequestViewHelper: Process() called\n" + request.getRequestURI() + " was the URI provided.\n"); //DEBUG
        switch (request.getRequestURI()) {

            case "/IBJK_Project_One/login.view":
                return "partials/login.html";

            case "/IBJK-Project-One/dashboard.view":
                return "partials/dashboard.html";

            case "/IBJK_Project_One/pasttickets.view":
                return "partials/pasttickets.html";

            default:
                log.info("RequestViewHelper called the default in switch case");
                return "/partials/index.html";
        }
    }
}