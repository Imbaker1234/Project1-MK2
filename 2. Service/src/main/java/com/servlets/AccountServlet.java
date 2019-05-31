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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.service.ErsUsersService;

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
			
			ErsUsers user = userService.validateCredentials(userinput[0], userinput[1]);
			String userJSON = mapper.writeValueAsString(user);
			writer.write(userJSON);
			
		} else if (userinput.length == 5) {
			
			ErsUsers user = userService.validateCredentials(userinput[0], userinput[1], userinput[2], userinput[3], userinput[4]);
			String userJSON = mapper.writeValueAsString(user);
			writer.write(userJSON);

		} 
	}

	public String[] nodeToArray(ArrayNode rootNode) {

		String[] array = new String[rootNode.size()];

		for (int i = 0; i < rootNode.size(); i++) {
			array[i] = rootNode.get(i).asText();

		}
		return array;
	}
	
	/*
	 * HOW TO CREATE JWT

2. Create new JwtConfig class in .util package

public static final String URI = "/**";
public static final String HEADER = "Authorization";
public static final String PREFIX = "Bearer ";
public static int EXPIRATION = 24 * 60 * 60; // token lasts one day long
public static final String SECRET = "JwtSecretKey";  //only put this here for educational purposes (otherwise put in a seperate forlder for security reasons)

static {
    // Create a signing key using the JWT secret key
    SignatureAlgorithm signatureAlg = SignatureAlgorithm.HS256
    byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET);
    SIGNING_KEY = new SecretKeySpec(secretBytes, signatureAlg.getJcaName());
}

private JwtConfig() {
    super();
    }


3. Create new JwtGenerator class in .util package

private static Logger log = LogManager.getLogger(JwtGenerator.class);

public static String createJwt(User subject){
    log.info("Creating new JWT for: " + subject.getUsername());

    // The JWT Signature Algorithm used to sign the token
    SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;

    long nowMillis = System.currentTimeMillis();

    // Configure the JWT and set its claims
    JwtBuilder builder = Jwts.builder()
        .setId(Integer.toString(subject.getId());
        .setSubject(subject.getUsername());
        .setIssuer("revature");
        .claim("role", subject.getRole().getRoleName()
        .setExpiration(new Date(nowMillis + JwtConfig.EXPIRATION))
        .signWith(sigAlg, JwtConfig.SIGNING_KEY)

    log.info("JWT successfully created");

    // Build the JWT and serialize it into a compact, URL-safe string
    return builder.compact();


(if you want to return a Principal)
1. create principal class model
2. Create new Principal in Authentication Servlet. 
    3. write.write(mapper.writeValueAsString(principal);
        
        String token = JwtGenerator.createJwt(authUser);

//    Add the token to the response within an Authorization header if storing the token in
//    localStorage on the client-side (vulnerable to XSS)

        response.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + token);
	 */

}
