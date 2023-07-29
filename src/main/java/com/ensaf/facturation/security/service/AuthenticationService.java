package com.ensaf.facturation.security.service;

import java.util.Map;
import java.util.Objects;

import com.ensaf.facturation.model.User;

import lombok.Getter;

public class AuthenticationService {
	
	// key : user, value: password
	private static Map<String, String> USERS_DB = Map.of(
			"user1", "pass1", "user2", "123", "admin", "pass"
	);

	@Getter
	private volatile static AuthenticationService instance = new AuthenticationService();

	private AuthenticationService() {
	}

	public User login(String username, String password) {
		// User user = userDao.findUserByUsername(username)
		// verifier si l utilisateur est actif, sinon throw une exception
		// hasher le password et le comparer avec user.getPassword(), si ko throw une exception
		String dbPassword = USERS_DB.get(username);
		if (Objects.equals(password, dbPassword)) {
			return User.builder().username(username).enabled(true).role("ROLE_AUTH").build();	
		}
		return null;
		
	}
	
	public User signup(User user) {
		// hasher password avant de l enregister dans la bd
		return null;
	}


}
