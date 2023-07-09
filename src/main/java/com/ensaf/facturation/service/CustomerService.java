package com.ensaf.facturation.service;

import java.util.List;

import com.ensaf.facturation.dao.CustomerDao;
import com.ensaf.facturation.model.Customer;

import lombok.Getter;
import lombok.Setter;

@Setter
public class CustomerService {
	
	@Getter
	private volatile static CustomerService instance = new CustomerService();
	
	private CustomerDao customerDao;

	private CustomerService() {
		customerDao = CustomerDao.getInstance();
	}
	
	
	public Customer create(Customer c) {
		// controler unicité de l'email
		if (customerDao.existsByEmail(c.getEmail())) {
			System.err.println("Email deja pris !");
			//TODO launch exception
			return null;
		}
		return customerDao.create(c);
	}
	
	public List<Customer> find(Customer filter) {
		return customerDao.find(filter);
	}

	public Customer findById(Long id) {
		return customerDao.findById(id);
	}

}
