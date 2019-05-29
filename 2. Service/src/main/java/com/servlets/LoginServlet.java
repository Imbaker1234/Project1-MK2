package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(LoginServlet.class);
	private final ErsUsersService userService = new ErsUsersService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request,response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("Login doPost reached.");
		
		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		response.setContentType("application/json");
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		ErsUsers user = userService.validateCredentials(username, password);
		
		String userJSON = mapper.writeValueAsString(user);
		
		writer.write(userJSON);
		
	}

}
