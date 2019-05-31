package com.util;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.SignatureAlgorithm;

public class JwtConfig {
	
	public static final String URI = "/**";
	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static int EXPIRATION = 24 * 60 * 60 * 1000; // token lasts one day long
	public static final String SECRET = "JwtSecretKey";
	public static final Key SIGNING_KEY;  //only put this here for educational purposes (otherwise put in a seperate forlder for security reasons)

	static {
	    // Create a signing key using the JWT secret key
	    SignatureAlgorithm signatureAlg = SignatureAlgorithm.HS256;
	    byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET);
	    SIGNING_KEY = new SecretKeySpec(secretBytes, signatureAlg.getJcaName());
	}

	private JwtConfig() {
	    super();
	    }

}
