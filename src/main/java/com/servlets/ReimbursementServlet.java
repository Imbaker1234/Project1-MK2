package com.servlets;

import com.POJO.Principal;
import com.POJO.ReimbPrinc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
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
import java.util.List;

@WebServlet("/dashboard")
public class ReimbursementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(ReimbursementServlet.class);
	private final ErsReimbursementService reimbService = new ErsReimbursementService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * doPatch method of servlet is called when a user wants to retrieve a table of
	 * reimbursements from the database. Employees will only have access to their
	 * history, but admins may retrieve their history, all reimbs, or a filtered
	 * grouping from all reimbs
	 * 
	 * This is also used when an admin wants to resolve a reimbursement request
	 * because doPatch was giving us problems
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("Reimb doPost() called.");

		try {

			PrintWriter writer = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			response.setContentType("application/json");

			ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
			String[] userinput = NodeToArray.convert(rootNode);

			Principal principal = (Principal) request.getAttribute("principal");
			String role = principal.getRole();

			switch (role) {

			case "employee":

				if (userinput[0].equals("pasttickets")) {
					List<ReimbPrinc> pasttickets = reimbService.viewPastTickets(principal);
					writer.write(mapper.writeValueAsString(pasttickets));

				}
				break;

			case "admin":

				if (userinput[0].equals("pasttickets")) {
					List<ReimbPrinc> pasttickets = reimbService.viewPastTickets(principal);
					writer.write(mapper.writeValueAsString(pasttickets));

				} else if (userinput[0].equals("alltickets")) {
					List<ReimbPrinc> allReimbs = reimbService.viewAllReimbs();
					writer.write(mapper.writeValueAsString(allReimbs));

				} else if (userinput[0].equals("Pending") || userinput[0].equals("Approved")
						|| userinput[0].equals("Denied")) {
					List<ReimbPrinc> filteredReimbs = reimbService.filterReimbs(userinput[0]);
					writer.write(mapper.writeValueAsString(filteredReimbs));

				} else if (userinput[1].equals("Approved") || userinput[1].equals("Denied")) {
					log.info("Update reimb input validated");
					Boolean updatedUserCheck = reimbService.approveDenyReimb(principal, userinput[0], userinput[1]);
					writer.write(mapper.writeValueAsString(updatedUserCheck));

				}
				break;
			}

		} catch (MismatchedInputException mie) {
			log.error(mie.getMessage());
			response.setStatus(400);

		} catch (Exception e) {
			log.error(e.getMessage());
			response.setStatus(500);

		}
	}

	/**
	 * doPut method of servlet is called when a user wants to add a reimbursement
	 * request to the database
	 */

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("Reimb doPut() called.");

		try {

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
			
		} catch (MismatchedInputException mie) {
			log.error(mie.getMessage());
			response.setStatus(400);

		} catch (Exception e) {
			log.error(e.getMessage());
			response.setStatus(500);

		}
	}

}