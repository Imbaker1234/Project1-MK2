package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    private static Logger log = LogManager.getLogger(RequestViewHelper.class);

    public static String process(HttpServletRequest request) {


        switch (request.getRequestURI()) {


            case "/IBJK-Project-One/login.view":
                log.info("RequestViewHelper called login.view");
                return "partials/login.html";

            case "/IBJK-Project-One/dashboard.view":
                log.info("RequestViewHelper called dashboard.view");
                return "partials/dashboard.html";

            case "/IBJK-Project-One/pasttickets.view":
                log.info("RequestViewHelper called pasttickets.view");
                return "partials/pasttickets.html";

            default:
                log.info("RequestViewHelper called the default in switch case");
                return "/partials/login.html";
        }
    }
}