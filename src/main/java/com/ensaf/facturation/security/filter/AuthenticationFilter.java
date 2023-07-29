package com.ensaf.facturation.security.filter;

import static com.ensaf.facturation.utils.Constants.*;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import com.ensaf.facturation.security.SecurityContextHolder;
import com.ensaf.facturation.security.model.UserAuthentication;
import com.ensaf.facturation.security.service.JwtTokenProvider;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AuthenticationFilter is a servlet filter that intercepts every request to the server.
 * It checks if the request contains a valid JWT token in the auth cookie and, if it does,
 * it sets the security context for the current session.
 */
@WebFilter(urlPatterns = "/*", filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private JwtTokenProvider tokenProvider = JwtTokenProvider.getInstance();

    boolean uriToSecure(String uri) {
        return !(uri.startsWith("/js") || uri.startsWith("/auth"));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
    	System.out.println("before do filter - AuthenticationFilter");
        
        try {
            // Process the request
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            String requestURI = request.getRequestURI();
            String uri = requestURI.replace(request.getContextPath(), "");

            if (uriToSecure(uri)) {
                Optional<Cookie> authCookie = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                        .filter(c -> AUTH_COOKIE.equals(c.getName()))
                        .findFirst();

                if (authCookie.isEmpty() || !tokenProvider.validateToken(authCookie.get().getValue())) {
                    System.out.println("utilisateur non connecté + uri : " + requestURI);
                 
                    response.sendRedirect(request.getContextPath() + AUTH_PATTERN + "?" + REDIRECT_URI + "=" + requestURI);
                    return;
                }

                // Parse the token and set the security context
                UserAuthentication userAuth = tokenProvider.parseToken(authCookie.get().getValue());
                SecurityContextHolder.setContext(userAuth);
            }

            chain.doFilter(req, res);
        } finally {
        	System.out.println("after do filter - AuthenticationFilter");
            // Always clean up the security context, regardless of what happens while processing the request
            SecurityContextHolder.clearContext();
        }
    }
}
