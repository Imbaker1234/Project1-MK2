package com.servlets;

import com.util.RequestViewHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("*.view")
public class FrontControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(FrontControllerServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.info("request recieved by FrontControllerServlet");


        //Requests a particular file path based on the view that is called.
		String returnHTML = RequestViewHelper.process(request);
        log.info("returnHTML: " + returnHTML);

        //Forwards the request and response
        log.info("Request:" + request.toString());
        log.info("Response:" + response.toString());
		request.getRequestDispatcher(returnHTML).forward(request, response);
	}

}