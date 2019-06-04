package com.servlets;

import com.POJO.ErsReimbursement;
import com.POJO.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.service.ErsReimbursementService;
import com.util.NodeToArray;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/dashboard")
public class ReimbursementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(ReimbursementServlet.class);
	private final ErsReimbursementService reimbService = new ErsReimbursementService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}
	
	/**
	 * doPatch method of servlet is called when a user wants to retrieve a table of reimbursements from the database.
	 * Employees will only have access to their history, but admins may retrieve their history, all reimbs, or a filtered
	 * grouping from all reimbs
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("Reimb doPost() called.");

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");

		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] userinput = NodeToArray.convert(rootNode);

		String input2 = userinput[0];
		Principal principal = (Principal) request.getAttribute("principal");
		String role = principal.getRole();

		switch (role) {

		case "employee":

			if (input2.equals("pasttickets")) {
				ArrayList<ErsReimbursement> pasttickets = reimbService.viewPastTickets(principal);
				writer.write(mapper.writeValueAsString(pasttickets));

			}
			break;

		case "admin":

			if (input2.equals("pasttickets")) {
				reimbService.viewPastTickets(principal);
				ArrayList<ErsReimbursement> pasttickets = reimbService.viewPastTickets(principal);
				writer.write(mapper.writeValueAsString(pasttickets));

			} else if (input2.equals("viewallreimbs")) {
				ArrayList<ErsReimbursement> allReimbs = reimbService.viewAllReimbs();
				writer.write(mapper.writeValueAsString(allReimbs));

			} else if (input2.equals("Pending") || input2.equals("Approved") || input2.equals("Denied")) {
				ArrayList<ErsReimbursement> filteredReimbs = reimbService.filterReimbs(input2);
				writer.write(mapper.writeValueAsString(filteredReimbs));
			}
			break;
		}
	}
	
	/**
	 * doPut method of servlet is called when a user wants to add a reimbursement request to the database
	 */

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("Reimb doPut() called.");

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");

		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] userinput = NodeToArray.convert(rootNode);

		Principal principal = (Principal) request.getAttribute("principal");
		Boolean reimbAdded = false;

			reimbAdded = reimbService.addReimbRequest(principal, userinput[0], userinput[1], userinput[2]);

			if (reimbAdded == true) {
				writer.write(mapper.writeValueAsString(reimbAdded));
			} else {
				writer.write(mapper.writeValueAsString(null));
			}
	}
	
	/**
	 * doPatch method of servlet is called when an admin wants to resolve a reimbursement request
	 */

	protected void doPatch(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("Reimb doPatch() called.");

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");

		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] userinput = NodeToArray.convert(rootNode);

		Principal principal = (Principal) request.getAttribute("principal");
		String role = principal.getRole();

		switch (role) {

		case "admin":

			if (userinput[1].equals("true") || userinput[1].equals("false")) {
				Boolean updatedUserCheck = reimbService.approveDenyReimb(principal, userinput[1], userinput[2]);
				writer.write(mapper.writeValueAsString(updatedUserCheck));

			}
			break;
		}

	}
}