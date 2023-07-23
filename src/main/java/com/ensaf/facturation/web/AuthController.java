package com.ensaf.facturation.web;

import static com.ensaf.facturation.utils.Constants.ACTION;
import static com.ensaf.facturation.utils.Constants.AUTH_COOKIE;
import static com.ensaf.facturation.utils.Constants.AUTH_PATTERN;
import static com.ensaf.facturation.utils.Constants.LOGIN;
import static com.ensaf.facturation.utils.Constants.LOGOUT;
import static com.ensaf.facturation.utils.Constants.PREFIX_PATH;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import com.ensaf.facturation.model.User;
import com.ensaf.facturation.security.service.AuthenticationService;
import com.ensaf.facturation.utils.RequestParams;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = AUTH_PATTERN + "/*")
public class AuthController extends HttpServlet {
	private AuthenticationService authenticationService = AuthenticationService.getInstance();
//  private static final String SECRET_KEY = "your-secret-key"; // on peut le recuperer a partir de fichier properties ...
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestParams params = new RequestParams(request);
		String requestURI = request.getRequestURI();
		String uri = requestURI.replace(request.getContextPath(), "").replace(AUTH_PATTERN, "");
		System.out.println("uri : '" + uri + "'");
		switch (uri) {
			case "":
			case LOGIN: {
				// affichage du formulaire d'authentification
		    	request.getRequestDispatcher(PREFIX_PATH + "/auth/login.jsp").forward(request, response);					
		    	break;
			}
			case LOGOUT: {
				Stream.of(request.getCookies())
						.filter(c -> AUTH_COOKIE.equals(c.getName()))
						.findFirst().ifPresent(c -> {
							System.out.println("logout : " + c.getValue() + "-" + c.getMaxAge());
							c.setMaxAge(0);
							//FIXME delete the cookie 
							response.addCookie(c);
						});
				response.sendRedirect(request.getContextPath() + AUTH_PATTERN);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + uri);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestParams params = new RequestParams(request);
//		Customer customer = getCustomer(params);
		String action = params.get(ACTION);
		System.out.println("do post auth");
		
		if (Objects.equals(action, "login")) {
			String username = params.get("username");
			String password = params.get("password");
			System.out.println("username : " + username);
			System.out.println("password : " + password);
			User user = authenticationService.login(username, password);
			if (user == null) {
				response.sendRedirect(request.getContextPath() + AUTH_PATTERN + "?error=1&username="+username);
			} else {
				System.out.println("auth controller - user : " + user);
				
//				request.getSession(true).setAttribute("auth", user); 
				String auth = new StringBuilder().append(user.getUsername()).append(":").toString();
//              String signature = CryptoUtils.sign(username, SECRET_KEY);
//              Cookie cookie = new Cookie("auth", username + ":" + signature);

				Cookie cookie = new Cookie(AUTH_COOKIE, auth);
				cookie.setHttpOnly(true);
				cookie.setSecure(true); // dans le cas de https
				response.addCookie(cookie);
				System.out.println("cookie created : " + cookie.getValue());
				response.sendRedirect(request.getContextPath() + CustomerController.URL_PATTERN);
				
			}
		}
//		} else if (Objects.equals(action, UPDATE)) {
//			customerService.update(customer);
//		} 
//		
//		response.sendRedirect(request.getContextPath() + URL_PATTERN);
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
}
