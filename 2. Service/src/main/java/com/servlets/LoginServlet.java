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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
		
		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		response.setContentType("application/json");
		
		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] results = new String[rootNode.size()];
		for(int i = 0; i < rootNode.size(); i++) {
			results[i] = rootNode.get(i).asText();
		}
		
		if(results.length == 2) {
			
			ErsUsers user = userService.validateCredentials(results[0], results[1]);
			
			String userJSON = mapper.writeValueAsString(user);
			
			writer.write(userJSON);
		} else if(results.length == 5) {
			
		} else {
			
		}

	}

}
