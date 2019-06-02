package com.servlets;

import com.POJO.ErsUsers;
import com.POJO.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.service.ErsUsersService;
import com.util.JwtConfig;
import com.util.JwtGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(AccountServlet.class);
	private final ErsUsersService userService = new ErsUsersService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.info("request recieved by AccountServlet");

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");

		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] userinput = nodeToArray(rootNode);
		
		if (userinput.length == 2) {
            log.info("AccountServlet.doPost() : Line 45 : Logging in user");
			ErsUsers user = userService.validateCredentials(userinput[0], userinput[1]);
            Principal principal = new Principal(user.getErsUsersId() + "", user.getErsUsername(),
                    user.getUserRoleName(user.getUserRoleId()));
			writer.write(mapper.writeValueAsString(principal));

			String token = JwtGenerator.createJwt(user);
			response.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + token);

        } else if (userinput.length == 5) { // This clause is entered register
            log.info("AccountServlet.doPost() : Line 55 : Registering new user");
			ErsUsers user = userService.validateCredentials(userinput[0], userinput[1], userinput[2], userinput[3], userinput[4]);
            Principal principal = new Principal(user.getErsUsersId() + "", user.getErsUsername(),
                    user.getUserRoleName(user.getUserRoleId()));
			writer.write(mapper.writeValueAsString(principal));
			
			String token = JwtGenerator.createJwt(user);
			response.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + token);

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
