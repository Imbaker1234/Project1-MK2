package com.filters;

import com.POJO.Principal;
import com.util.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class TokenAuthenticationFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(TokenAuthenticationFilter.class);

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Request intercepted by TokenAuthenticationFilter.doFilter()");
		String header = request.getHeader(JwtConfig.HEADER);
		log.info("TokenAuthenticationFilter.doFilter() was passed " + request.getHeader(JwtConfig.HEADER) + "\n" +
				"as the header.");
		if (header == null || !header.startsWith(JwtConfig.PREFIX)) {
			log.warn("No matching JWT Header. Request originates from an unauthenticated origin.");
			request.setAttribute("isAuthenticated", false);
			chain.doFilter(request, response);
			return;
			
		}

		String token = header.replaceAll(JwtConfig.PREFIX, "");

		try {

			Claims claims = Jwts.parser().setSigningKey(JwtConfig.SIGNING_KEY).parseClaimsJws(token).getBody();
			log.info("Parsing token holder's claims.");
			Principal principal = new Principal();
			principal.setId(Integer.parseInt(claims.getId()));
			principal.setUsername(claims.getSubject());
			principal.setRole(claims.get("role", String.class));

			request.setAttribute("isAuthenticated", true);
			request.setAttribute("principal", principal);

		} catch (Exception e) {
			log.error(e.getMessage());

		}
		chain.doFilter(request, response);
	}

}
