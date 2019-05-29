package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.service.ErsUsersService;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(UserServlet.class);
	private final ErsUsersService userService = new ErsUsersService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		PrintWriter writer = response.getWriter();
		
		response.setContentType("application/json");
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		
		//List<Users> users = userService.getAllUsers();
		//String userJSON = mapper.writeValueAsString(users);

		//writer.write(userJSON);
	}

}
