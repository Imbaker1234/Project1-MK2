package com.servlets;

import com.POJO.ErsUsers;
import com.POJO.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.service.ErsUsersService;
import com.util.JwtConfig;
import com.util.JwtGenerator;
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

@WebServlet("/account")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(UserServlet.class);
	private final ErsUsersService userService = new ErsUsersService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("AccountServlet: Line 37 : AccountServlet.doPost() : Received Request.");

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");

		ArrayNode rootNode = mapper.readValue(request.getReader(), ArrayNode.class);
		String[] userinput = NodeToArray.convert(rootNode);
		
		if (userinput.length == 2) {

			log.info("AccountServlet : AccountServlet.doPost() : Logging in user");
			ErsUsers user = userService.validateCredentials(userinput[0], userinput[1]);
            Principal principal = new Principal(user.getErsUsersId(), user.getErsUsername(),
                    user.getUserRoleName(user.getUserRoleId()));
			writer.write(mapper.writeValueAsString(principal));

			String token = JwtGenerator.createJwt(user);
			log.info("AccountServlet: Line 56 : doPost() : JWT returned\n");
			response.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + token);
            log.info("AccountServlet: Line 56 : AccountServlet.doPost() : Sending out response");
            
        } else if (userinput.length == 5) { // This clause is entered register

			log.info("AccountServlet : AccountServlet.doPost() : Registering new user");
			ErsUsers user = userService.validateCredentials(userinput[0], userinput[1], userinput[2], userinput[3], userinput[4]);
            Principal principal = new Principal(user.getErsUsersId(), user.getErsUsername(),
                    user.getUserRoleName(user.getUserRoleId()));
			writer.write(mapper.writeValueAsString(principal));

            String token = JwtGenerator.createJwt(user);
			response.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + token);

		} 
	}
}
