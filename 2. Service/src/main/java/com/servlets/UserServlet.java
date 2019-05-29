package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.POJO.ErsUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.ErsUsersService;

@WebServlet("/dashboard")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(UserServlet.class);
	private final ErsUsersService userService = new ErsUsersService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("benis");
		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		response.setContentType("application/json");
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		
		ErsUsers user = userService.validateCredentials();
		
		String userJSON = mapper.writeValueAsString(user);

		writer.write(userJSON);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("POST METHOD ACTIVATED");
		
	}

}
