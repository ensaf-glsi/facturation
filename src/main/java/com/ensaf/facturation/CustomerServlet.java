package com.ensaf.facturation;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.ensaf.facturation.model.Customer;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/clients/*")
public class CustomerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    Customer newCustomer(Long id) {
    	Customer c = new Customer();
    	c.setId(id);
    	c.setName("name " + id);
    	c.setEmail("email " + id);
    	c.setPhone("phone " + id);
    	c.setAddress("address " + id);
    	return c;
    }

	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
//    	List<Customer> list = new ArrayList<>();
//    	list.add(newCustomer(1l));
//    	list.add(newCustomer(2l));
//    	list.add(newCustomer(3l));
//    	list.add(newCustomer(4l));
    	List<Customer> list = Stream.of(1l, 2l, 3l, 4l).map(this::newCustomer).toList();
    	request.setAttribute("list", list);
        Map<String, String> columns = new LinkedHashMap<>();
        columns.put("id", "Réf.");
        columns.put("name", "Nom");
        columns.put("email", "Email");
        columns.put("phone", "Tél.");
        columns.put("address", "Adresse");

        request.setAttribute("columns", columns);

    	request.getRequestDispatcher("/views/customer/list.jsp").forward(request, response);
    }
}