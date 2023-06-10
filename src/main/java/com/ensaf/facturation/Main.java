package com.ensaf.facturation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.ensaf.facturation.config.DatabaseConnectionPool;
import com.ensaf.facturation.model.User;

public class Main {
	
	void sleep(Integer seconds) {
		try {
			Thread.sleep(seconds * 1000); // attendre une seconde
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Main()  {
		jdbcExample();
	}
	
	void lombokExample() {
		User user1 = newUSer("1");
		sleep(1);
		User user2 = newUSer("1");
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

	private User newUSer(String id) {
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
