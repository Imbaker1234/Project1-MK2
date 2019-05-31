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

import com.POJO.ErsReimbursement;
import com.POJO.ErsUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.service.ErsReimbursementService;

@WebServlet("/dashboard")
public class ReimbursementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(ReimbursementServlet.class);
	private final ErsReimbursementService reimbService = new ErsReimbursementService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.info("Reimb doPost() called.");
		
		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		
		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] userinput = nodeToArray(rootNode);
		
		
	}
	
	public String[] nodeToArray(ArrayNode rootNode) {

		String[] array = new String[rootNode.size()];

		for (int i = 0; i < rootNode.size(); i++) {
			array[i] = rootNode.get(i).asText();

		}
		return array;
	}
} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.info("request recieved by ReimbursementServlet");
		
		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		ErsReimbursement newReimb = null;
		
		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] userinput = nodeToArray(rootNode);
		ErsUsers loggedUser = new ErsUsers(userinput[0], userinput[1], userinput[2], null, null, null, null);
		//ErsReimbursement reimbReq  = new ErsReimbursement(userinput[3],userinput[4],userinput[5],userinput[6],userinput[7], userinput[8],
		//		userinput[9], userinput[10],userinput[11], userinput[12]);

		
		//reimbService.viewPastTickets(user);
		
	}
	
	public String[] nodeToArray(ArrayNode rootNode) {

		String[] array = new String[rootNode.size()];

		for (int i = 0; i < rootNode.size(); i++) {
			array[i] = rootNode.get(i).asText();

		}
		return array;
	}
}
*/
