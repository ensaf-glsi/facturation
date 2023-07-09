package com.ensaf.facturation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.ensaf.facturation.config.DatabaseConnectionPool;
import com.ensaf.facturation.dao.CustomerDao;
import com.ensaf.facturation.model.Customer;
import com.ensaf.facturation.model.User;

public class Main {
	static int count = 1;
	
	CustomerDao customerDao = CustomerDao.getInstance();
	
	void sleep(Integer seconds) {
		try {
			Thread.sleep(seconds * 1000); // attendre une seconde
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Main()  {
//		jdbcExample();
//		insertCustomer();
//		updateCustomer();
//		deleteCustomer();
//		findCustomerById();
		findCustomer();
//		findCustomerMultiCriteria();
	}
	
	// dao ou repository : couche d'acces aux données 
	
	private void findCustomerById() {
		System.out.println(customerDao.findById(-1L));
	}

	private void findCustomer() {
		System.out.println("======== criteria null");
		System.out.println(customerDao.find((String) null));
		System.out.println("======== criteria empty");
		System.out.println(customerDao.find(""));
		System.out.println("======== criteria not empty");
		System.out.println(customerDao.find("name1"));
		System.out.println("======== criteria id");
		System.out.println(customerDao.find("7"));
	}

	private void findCustomerMultiCriteria() {
		System.out.println("======== no criteria");
		System.out.println(customerDao.find(new Customer()));
		System.out.println("======== find by id");
		System.out.println(customerDao.find(Customer.builder().id(4L).build()));
		System.out.println("======== find by address");
		System.out.println(customerDao.find(Customer.builder().address("ad").build()));
		System.out.println("======== find by name and phone");
		System.out.println(customerDao.find(Customer.builder().name("name1").phone("phone2").build()));
	}

	void lombokExample() {
		User user1 = newUSer(1L);
		sleep(1);
		User user2 = newUSer(1L);
		System.out.println(user1);
		System.out.println(user1.equals(user2));
	}
	
	void jdbcExample() {
		try (Connection connection = DatabaseConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                // Handle result
            	System.out.println(resultSet.getString("id"));
            	System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            // Handle exception
        }
	}
	
	Customer newCustomer(int n) {
		return Customer.builder().name("name" + n)
			.phone("phone" + n).email("email" + n)
			.address("address" + n).build();
	}
	
	void insertCustomer() {
		Customer c = customerDao.create(newCustomer(count++));
		System.out.println(c);
	}

	void updateCustomer() {
		Customer c = customerDao.update(
				Customer.builder().id(5l).name("name" + 5)
				.email("email" + 5).address("address" + 5).build()
				
		);
		System.out.println(c);
	}

	void deleteCustomer() {
		System.out.println(customerDao.delete(8l));
	}

	
	private User newUSer(Long id) {
//		User user = new User();
//		user.setId(id);
//		user.setUsername("user" + id);
//		user.setPassword("1234");
//		user.setEnabled(true);
//		user.setCreationDate(new Date());
//		user.setPasswordExpiryDate(new Date());
//		return user;
		
		return User.builder()
				.id(id)
				.username("user" + id)
				.password("1234")
				.enabled(true)
				.creationDate(new Date())
				.passwordExpiryDate(new Date())
				.build();
	}

	public static void main(String[] args) {
		new Main();
	}

}
