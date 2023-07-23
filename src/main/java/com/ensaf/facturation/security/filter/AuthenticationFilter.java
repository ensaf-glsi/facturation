package com.ensaf.facturation.security.filter;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static com.ensaf.facturation.utils.Constants.*;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
	
	// /js/*, /auth/*
	
	boolean uriToSecure(String uri) {
		return !(uri.startsWith("/js") || uri.startsWith("/auth"));
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String requestURI = request.getRequestURI();
		String uri = requestURI.replace(request.getContextPath(), "");
		System.out.println("filter request uri : " + uri);
//		System.out.println("session user : " + request.getSession().getAttribute("auth"));
		if (uriToSecure(uri)) {
			Optional<Cookie> authCookie = Stream.of(request.getCookies())
					.filter(c -> AUTH_COOKIE.equals(c.getName()))
					.findFirst();
			if (authCookie.isEmpty()) {
				System.out.println("utilisateur non connecté");
				response.sendRedirect(request.getContextPath() + AUTH_PATTERN);
				return;
			}
			//TODO get user info
			System.out.println("auth cookie : " + authCookie.get().getValue());
		}
		chain.doFilter(req, res);
	}


	
	
	
//  private static final String SECRET_KEY = "your-secret-key"; // on peut le recuperer a partir de fichier properties ...
//
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//		Cookie[] cookies = request.getCookies();
//
//		if (cookies != null) {
//			Optional<Cookie> authCookie = Arrays.stream(cookies).filter(c -> COOKIE_NAME.equals(c.getName()))
//					.findFirst();
//
//			if (authCookie.isPresent()) {
//				String[] values = authCookie.get().getValue().split(":");
//
//				if (values.length == 2) {
//					String username = values[0];
//					String providedSignature = values[1];
//
//					try {
//						String expectedSignature = CryptoUtils.sign(username, SECRET_KEY);
//
//						if (providedSignature.equals(expectedSignature)) {
//							// Signature is correct, user is authenticated
//							// Continue the request
//							filterChain.doFilter(servletRequest, servletResponse);
//							return;
//						}
//					} catch (NoSuchAlgorithmException | InvalidKeyException e) {
//						e.printStackTrace();
//						// Handle exception properly
//					}
//				}
//			}
//		}
//
//		// If no valid auth cookie found, redirect to login
//		response.sendRedirect(request.getContextPath() + "/login.jsp");
//	}

}
