package com.ensaf.facturation.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import com.ensaf.facturation.model.Customer;
import com.ensaf.facturation.service.CustomerService;
import static com.ensaf.facturation.utils.Constants.*;
import com.ensaf.facturation.utils.RequestParams;

@WebServlet(urlPatterns = CustomerController.URL_PATTERN + "/*")
public class CustomerController extends HttpServlet {
	final static String URL_PATTERN = "/customers";
		
	private static final long serialVersionUID = 1L;
	private CustomerService customerService = CustomerService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("CustomerController - doGet");
		RequestParams params = new RequestParams(request);
		String requestURI = request.getRequestURI();
		String uri = requestURI.replace(request.getContextPath(), "").replace(URL_PATTERN, "");
		System.out.println("uri : '" + uri + "'");
		switch (uri) {
			case EDIT: {
				Long id = params.getLong("id");
				if (id != null) {
					request.setAttribute("item", customerService.findById(id));
				}
				// affichage du formulaire pour creer ou modifier un client
		    	request.getRequestDispatcher(PREFIX_PATH + "/customer/edit.jsp").forward(request, response);					
		    	break;
			}
			case DELETE: {
				Long id = params.getLong("id");
				customerService.deleteById(id);
				response.sendRedirect(request.getContextPath() + URL_PATTERN);
		    	break;
			}
			case "": {
				// affichage de la liste des clients
				Customer filter = getCustomer(params);
		    	request.setAttribute("filter", filter);
		    	request.setAttribute("list", customerService.find(filter));
		        String requestedWithHeader = request.getHeader("X-Requested-With");
		        System.out.println("request with " +  requestedWithHeader);
		        String jspView = "/customer/list.jsp";
		        if (Objects.equals(requestedWithHeader, "XMLHttpRequest")) {
		            jspView = "/customer/list-fragment.jsp";
		        }
		    	request.getRequestDispatcher(PREFIX_PATH + jspView).forward(request, response);
		    	break;				
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + uri);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestParams params = new RequestParams(request);
		Customer customer = getCustomer(params);
		String action = params.get(ACTION);
		if (Objects.equals(action, CREATE)) {
			customerService.create(customer);
		} else if (Objects.equals(action, UPDATE)) {
			customerService.update(customer);
		} 
		
		response.sendRedirect(request.getContextPath() + URL_PATTERN);
	}
	
	private Customer getCustomer(RequestParams params) {
		return Customer.builder()
				.id(params.getLong("id"))
				.name(params.get("name"))
				.email(params.get("email"))
				.phone(params.get("phone"))
				.address(params.get("address"))
				.build();
	}

}
