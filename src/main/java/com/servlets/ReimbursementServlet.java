package com.servlets;

import com.POJO.ErsReimbursement;
import com.POJO.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.service.ErsReimbursementService;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
		log.info("ReimbursementServlet.doGet() called");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("Reimb doPost() called.");

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");

		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] userinput = nodeToArray(rootNode);

		String input2 = userinput[0];
		Principal principal = (Principal) request.getAttribute("principal");
		String role = principal.getRole();

		switch (role) {
//TODO benis convert ROLES TO INTEGER FOR REIMBS AND PRINCIPAL
		case "employee":
			log.info("ReimbursementServlet.doPost() : Line 55 : Case 1 : \"employee\"");
			if (input2.equals("pasttickets")) {
				log.info("Received input == \"pasttickets\": Servlet calling for past tickets belonging to the " +
						"principal");
				ArrayList<ErsReimbursement> pasttickets = reimbService.viewPastTickets(principal);
				writer.write(mapper.writeValueAsString(pasttickets));

			} else {
				log.info("Recieved input != \"pasttickets\": Servlet calling to add Reimbursement");
				Boolean reimbAdded = reimbService.addReimbRequest(principal, userinput[0], userinput[1], userinput[2], userinput[3]);
				if (reimbAdded == true) {
					writer.write(mapper.writeValueAsString(reimbAdded));
				} else {
					writer.write(mapper.writeValueAsString(null));
				}
			}
			break;

			case "admin": //TODO Discuss removing the pasttickets method from admin as they should be viewing all tickets
				// at all times.
				log.info("ReimbursementServlet.doPost() : Line 55 : Case 1 : \"admin\": Servlet calling for tickets" +
						"belonging to the principal");
			if (input2.equals("pasttickets")) {
				reimbService.viewPastTickets(principal);
				ArrayList<ErsReimbursement> pasttickets = reimbService.viewPastTickets(principal);
				writer.write(mapper.writeValueAsString(pasttickets));

			} else if (input2.equals("viewallreimbs")) {
				log.info("Recieved input != \"viewallreimbs\": Servlet calling to add Reimbursement");
				ArrayList<ErsReimbursement> allReimbs = reimbService.viewAllReimbs();
				writer.write(mapper.writeValueAsString(allReimbs));

				//TODO Discuss why this checks against any value instead of each individual value.
			} else if (input2.equals("Pending") || input2.equals("Approved") || input2.equals("Denied")) {
				ArrayList<ErsReimbursement> filteredReimbs = reimbService.filterReimbs(input2);
				writer.write(mapper.writeValueAsString(filteredReimbs));

				//TODO Discuss why this runs regardless of true or false.
			} else if (userinput[1].equals("true") || userinput[1].equals("false")) {
				Boolean updatedUserCheck = reimbService.approveDenyReimb(principal, userinput[1], userinput[2]);
				writer.write(mapper.writeValueAsString(updatedUserCheck));

			} else {
				log.info("ReimbursementServlet calling for a new Reimbursement.");
				Boolean reimbAdded = reimbService.addReimbRequest(principal, userinput[0], userinput[1], userinput[2], userinput[3]);
				if (reimbAdded == true) {
					writer.write(mapper.writeValueAsString(reimbAdded));
				} else {
					writer.write(mapper.writeValueAsString(null));
				}

			}
			break;
		}

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
 * @Override protected void doPost(HttpServletRequest request,
 * HttpServletResponse response) throws IOException, ServletException {
 * log.info("request recieved by ReimbursementServlet");
 * 
 * PrintWriter writer = response.getWriter(); ObjectMapper mapper = new
 * ObjectMapper(); ErsReimbursement newReimb = null;
 * 
 * ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
 * String[] userinput = nodeToArray(rootNode); ErsUsers loggedUser = new
 * ErsUsers(userinput[0], userinput[1], userinput[2], null, null, null, null);
 * //ErsReimbursement reimbReq = new
 * ErsReimbursement(userinput[3],userinput[4],userinput[5],userinput[6],
 * userinput[7], userinput[8], // userinput[9], userinput[10],userinput[11],
 * userinput[12]);
 * 
 *
 * //reimbService.getTickets(user);
 * 
 * }
 * 
 * public String[] nodeToArray(ArrayNode rootNode) {
 * 
 * String[] array = new String[rootNode.size()];
 * 
 * for (int i = 0; i < rootNode.size(); i++) { array[i] =
 * rootNode.get(i).asText();
 * 
 * } return array; } }
 */
