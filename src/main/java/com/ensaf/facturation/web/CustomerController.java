package com.ensaf.facturation.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ensaf.facturation.model.Customer;
import com.ensaf.facturation.service.CustomerService;
import com.ensaf.facturation.utils.RequestParams;

@WebServlet(urlPatterns = CustomerController.URL_PATTERN + "/*")
public class CustomerController extends HttpServlet {
	private final static String URL_PATTERN = "/customers";
	
	private final static String EDIT = "/edit";
	
	private static final long serialVersionUID = 1L;
	private CustomerService customerService = CustomerService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		    	request.getRequestDispatcher("/views/customer/edit.jsp").forward(request, response);					
		    	break;
			}
			case "": {
				// affichage de la liste des clients
				Customer filter = getCustomer(params);
		    	request.setAttribute("filter", filter);
		    	request.setAttribute("list", customerService.find(filter));
		    	request.getRequestDispatcher("/views/customer/list.jsp").forward(request, response);
		    	break;				
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + uri);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestParams params = new RequestParams(request);
		Customer customer = getCustomer(params);
		customerService.create(customer);
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
