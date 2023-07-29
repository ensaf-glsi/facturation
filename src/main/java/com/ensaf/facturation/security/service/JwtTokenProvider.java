package com.ensaf.facturation.security.service;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import com.ensaf.facturation.config.AppProperties;
import com.ensaf.facturation.security.model.UserAuthentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

/**
 * JwtTokenProvider is a class that handles the creation, validation and data retrieval of JWT tokens.
 */
public class JwtTokenProvider {
	private String secretKey = AppProperties.getInstance().getProperty("jwt.secret");
	/**
     * The secret key used to sign the JWTs.
     */
    private SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    /**
     * The validity of the JWT in milliseconds. Set to 1 hour.
     */
    private final long validityInMilliseconds = 3600000; 

    @Getter
	private volatile static JwtTokenProvider instance = new JwtTokenProvider();

	private JwtTokenProvider() {}


    /**
     * Create a JWT token.
     * @param userAuth the user authentication object containing the username and roles of the user.
     * @return a JWT token.
     */
    public String createToken(UserAuthentication userAuth) {
        Claims claims = Jwts.claims().setSubject(userAuth.getUsername());
        claims.put("roles", userAuth.getRoles());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(key).compact();
    }

    /**
     * Validate a JWT token.
     * @param token the token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parse a JWT token and retrieve the user authentication information.
     * @param token the token.
     * @return a UserAuthentication object containing the username and roles of the user.
     */
    @SuppressWarnings("unchecked")
    public UserAuthentication parseToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        UserAuthentication userAuth = UserAuthentication.builder()
        		.username(claims.getSubject())
        		.roles((List<String>) claims.get("roles"))
        		.build();
        return userAuth;
    }
}
