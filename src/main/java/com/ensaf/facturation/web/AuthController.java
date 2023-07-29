package com.ensaf.facturation.web;

import static com.ensaf.facturation.utils.Constants.*;

import java.io.IOException;
import java.util.Objects;

import com.ensaf.facturation.model.User;
import com.ensaf.facturation.security.SecurityContextHolder;
import com.ensaf.facturation.security.model.UserAuthentication;
import com.ensaf.facturation.security.service.AuthenticationService;
import com.ensaf.facturation.security.service.JwtTokenProvider;
import com.ensaf.facturation.utils.RequestParams;
import com.ensaf.facturation.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This servlet is responsible for handling the authentication logic of the application.
 * It responds to both GET and POST requests.
 */
@WebServlet(urlPatterns = AUTH_PATTERN + "/*")
public class AuthController extends HttpServlet {

    private AuthenticationService authenticationService = AuthenticationService.getInstance();
    private JwtTokenProvider tokenProvider = JwtTokenProvider.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestParams params = new RequestParams(request);
        String requestURI = request.getRequestURI();
        String uri = requestURI.replace(request.getContextPath(), "").replace(AUTH_PATTERN, "");
        
        switch (uri) {
            case "":
            case LOGIN: {
                // Show login form
                request.getRequestDispatcher(PREFIX_PATH + "/auth/login.jsp").forward(request, response);                    
                break;
            }
            case LOGOUT: {
                // Clear the security context and remove the authentication cookie
                SecurityContextHolder.clearContext();
                Cookie cookie = new Cookie(AUTH_COOKIE, "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                response.sendRedirect(request.getContextPath() + AUTH_PATTERN);
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + uri);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("AuthController - doPost");
        RequestParams params = new RequestParams(request);
        String action = params.get(ACTION);

        if (Objects.equals(action, "login")) {
            String username = params.get("username");
            String password = params.get("password");

            User user = authenticationService.login(username, password);
            String redirectUri = params.get(REDIRECT_URI);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + AUTH_PATTERN + "?error=1&username="+username + "&" + REDIRECT_URI + "=" + redirectUri);
            } else {
                // Create a token and set it in a cookie
                UserAuthentication userAuthentication = UserAuthentication
                		.builder().username(username).roles(user.getRoles()).build();
                String token = tokenProvider.createToken(userAuthentication);
                Cookie authCookie = new Cookie(AUTH_COOKIE, token);
                authCookie.setHttpOnly(true);
                authCookie.setSecure(true); // If https
                response.addCookie(authCookie);

                // Set the security context
                SecurityContextHolder.setContext(userAuthentication);
                String redirect = request.getContextPath() + CustomerController.URL_PATTERN;
                if (StringUtils.isNotEmpty(redirectUri)) {
                	redirect = redirectUri;
                }
                response.sendRedirect(redirect);
            }
        }
    }
}

	/*
	 * 
	 * Un cookie HTTP comprend plusieurs attributs qui influencent son comportement.
	 * 
	 * - Nom : Il s'agit simplement du nom du cookie. - Valeur : C'est la valeur du
	 * cookie. C'est ce qui est utilisé pour stocker des informations sur le côté
	 * client. - Domaine : Ce paramètre définit les domaines auxquels le cookie sera
	 * envoyé. Par exemple, si vous définissez le domaine à "example.com", le cookie
	 * sera également envoyé aux sous-domaines comme "www.example.com".
	 * 
	 * - Chemin : Ce paramètre définit le chemin pour lequel le cookie est valide.
	 * Si vous définissez le chemin à "/path", le cookie sera envoyé pour toutes les
	 * requêtes à "example.com/path".
	 * 
	 * - Max-Age et Expires : Ces deux attributs sont utilisés pour définir la durée
	 * de vie d'un cookie. "Max-Age" définit la durée de vie en secondes, tandis que
	 * "Expires" définit une date spécifique à laquelle le cookie expirera.
	 * 
	 * - Secure : Si cet attribut est défini, le cookie ne sera envoyé que sur une
	 * connexion sécurisée (HTTPS). C'est important pour protéger les données
	 * sensibles.
	 * 
	 * - HttpOnly : Si cet attribut est défini, le cookie ne peut pas être accédé
	 * par des scripts côté client comme JavaScript. C'est une mesure de sécurité
	 * importante pour prévenir les attaques de type cross-site scripting (XSS).
	 * 
	 * - SameSite : Cet attribut peut avoir trois valeurs : "Strict", "Lax" ou
	 * "None". Il est utilisé pour contrôler si le cookie est envoyé avec des
	 * requêtes cross-site. "Strict" signifie que le cookie n'est envoyé qu'avec des
	 * requêtes de même site. "Lax" signifie que le cookie est envoyé avec des
	 * requêtes de même site et avec des requêtes cross-site top-level GET. "None"
	 * signifie que le cookie est envoyé avec toutes les requêtes cross-site, à
	 * condition que l'attribut "Secure" soit également défini.
	 */