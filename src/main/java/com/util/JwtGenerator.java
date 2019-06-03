package com.util;

import com.POJO.ErsUsers;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class JwtGenerator {

	private static Logger log = LogManager.getLogger(JwtGenerator.class);

	public static String createJwt(ErsUsers subject) {
		log.info("Creating new JWT for: " + subject.getErsUsername());

		// The JWT Signature Algorithm used to sign the token
		SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();

		// Configure the JWT and set its claims
		JwtBuilder builder = Jwts.builder().setId(subject.getErsUsersId() + "").setSubject(subject.getErsUsername())
				.setIssuer("The IBJKP1 Gods").claim("role", subject.getUserRoleName(subject.getUserRoleId()))
				.setExpiration(new Date(nowMillis + JwtConfig.EXPIRATION)).signWith(sigAlg, JwtConfig.SIGNING_KEY);

		log.info("JWT successfully created");

		// Build the JWT and serialize it into a compact, URL-safe string
		return builder.compact();

	}
	
	private JwtGenerator() {
		super();
	}
}
