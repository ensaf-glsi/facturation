package com.ensaf.facturation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ensaf.facturation.config.DatabaseConnectionPool;
import com.ensaf.facturation.model.Customer;
import com.ensaf.facturation.utils.Predicate;
import com.ensaf.facturation.utils.StringUtils;

import lombok.Cleanup;

/* C: create
 * R: read
 * U: update
 * D: delete
 */
public class CustomerDao {

	/**
	 * Crée un nouveau client dans la base de données et retourne le client avec
	 * l'ID généré.
	 * 
	 * @param customer l'objet client à créer dans la base de données
	 * @return l'objet client avec l'ID généré, ou null si une exception SQL se produit
	 */
	public Customer create(Customer customer) {
		// Récupère une connexion à la base de données
		try (Connection connection = DatabaseConnectionPool.getConnection()) {
			// Définit la requête SQL pour insérer un nouveau client
			String sql = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";

			// Prépare la requête SQL et indique que nous voulons récupérer les clés générées
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// Remplit les valeurs de la requête SQL avec les informations du client
			preparedStatement.setObject(1, customer.getName());
			preparedStatement.setString(2, customer.getEmail());
			preparedStatement.setString(3, customer.getPhone());
			preparedStatement.setString(4, customer.getAddress());

			// Exécute la requête SQL
			preparedStatement.executeUpdate();

			// Récupère les clés générées par l'insertion
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				// Si une clé a été générée, la définit comme l'ID du client et retourne le client
				customer.setId(generatedKeys.getLong(1));
			}
			return customer;

		} catch (SQLException e) {
			// Si une exception SQL se produit, imprime un message d'erreur et la pile d'appels
			System.err.println("SQLException occurred while creating a customer: " + e.getMessage());
			e.printStackTrace();
			return null; // Retourne null si une exception SQL se produit
		}
	}

	public Customer update(Customer customer) {
		// Récupère une connexion à la base de données
		try (Connection connection = DatabaseConnectionPool.getConnection()) { 

			// Définit la requête SQL pour mettre à jour un client
			String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ? "
					+ "WHERE id = ?";

			// Prépare la requête SQL et indique que nous voulons récupérer les clés générées
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			// Remplit les valeurs de la requête SQL avec les informations du client
			preparedStatement.setObject(1, customer.getName());
			preparedStatement.setObject(2, customer.getEmail());
			preparedStatement.setObject(3, customer.getPhone());
			preparedStatement.setObject(4, customer.getAddress());
			preparedStatement.setObject(5, customer.getId());

			// Exécute la requête SQL
			int rows = preparedStatement.executeUpdate();
			if (rows == 0) {
				System.err.println("No customer found with id: " + customer.getId());
				return null;
			}
			return customer;

		} catch (SQLException e) {
			// Si une exception SQL se produit, imprime un message d'erreur et la pile d'appels
			System.err.println("SQLException occurred while upating a customer: " + e.getMessage());
			e.printStackTrace();
			return null; // Retourne null si une exception SQL se produit
		}
	}
	
	public boolean delete(Long id) {
		// Récupère une connexion à la base de données
		try (Connection connection = DatabaseConnectionPool.getConnection()) { 

			// Définit la requête SQL pour supprimer un client
			String sql = "DELETE FROM customers WHERE id = ?";

			// Prépare la requête SQL et indique que nous voulons récupérer les clés générées
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			// Remplit les valeurs de la requête SQL avec les informations du client
			preparedStatement.setObject(1, id);

			// Exécute la requête SQL
			int rows = preparedStatement.executeUpdate();
			if (rows == 0) {
				System.err.println("No customer found with id: " + id);
				return false;
			}
			return true;

		} catch (SQLException e) {
			// Si une exception SQL se produit, imprime un message d'erreur et la pile d'appels
			System.err.println("SQLException occurred while deleting a customer: " + e.getMessage());
			e.printStackTrace();
			return false; // Retourne null si une exception SQL se produit
		}
	}
	
	public Customer findById(Long id) {
		// Récupère une connexion à la base de données
		try (Connection connection = DatabaseConnectionPool.getConnection()) { 

			// Définit la requête SQL pour supprimer un client
			String sql = "SELECT * FROM customers WHERE id = ?";

			// Prépare la requête SQL et indique que nous voulons récupérer les clés générées
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			// Remplit les valeurs de la requête SQL avec les informations du client
			preparedStatement.setObject(1, id);

			// Exécute la requête SQL
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return toCustomer(rs);
			} else {
				System.out.println("No customer found with id : " + id);
				return null;
			}
		} catch (SQLException e) {
			// Si une exception SQL se produit, imprime un message d'erreur et la pile d'appels
			System.err.println("SQLException occurred while deleting a customer: " + e.getMessage());
			e.printStackTrace();
			return null; // Retourne null si une exception SQL se produit
		}
	}
	
	public List<Customer> find(String criteria) {
		List<Customer> result = new ArrayList<>();
		criteria = criteria == null ? "" : criteria;
		// Récupère une connexion à la base de données
		try (Connection connection = DatabaseConnectionPool.getConnection()) { 

			// Définit la requête SQL pour supprimer un client
			String sql = "SELECT * FROM customers WHERE id = ? or name like ? or email like ? or phone like ? or address like ?";

			// Prépare la requête SQL et indique que nous voulons récupérer les clés générées
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			// Remplit les valeurs de la requête SQL avec les informations du client
			preparedStatement.setObject(1, criteria);
			String c = "%" + criteria + "%";
			preparedStatement.setObject(2, c);
			preparedStatement.setObject(3, c);
			preparedStatement.setObject(4, c);
			preparedStatement.setObject(5, c);

			// Exécute la requête SQL
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				result.add(toCustomer(rs));
			}
		} catch (SQLException e) {
			// Si une exception SQL se produit, imprime un message d'erreur et la pile d'appels
			System.err.println("SQLException occurred while deleting a customer: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	//id, 
	//name, phone
	//email, phone
	//address
	public List<Customer> find(Customer customer) {
	    List<Customer> result = new ArrayList<>();

	    try (Connection connection = DatabaseConnectionPool.getConnection()) { 

	        // Crée une liste de prédicats en fonction des champs remplis de l'objet client
	        List<Predicate> predicates = buildPredicates(customer);
	        
	        // Construit la requête SQL en utilisant les prédicats
		    String sql = "SELECT * FROM customers" + buildQueryPredicate(predicates);
	        System.out.println("query : " + sql);

	        PreparedStatement preparedStatement = connection.prepareStatement(sql);

	        // Définit les paramètres de la requête SQL
	        setSqlParameters(preparedStatement, predicates);

	        ResultSet rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            result.add(toCustomer(rs));
	        }
	    } catch (SQLException e) {
	        System.err.println("SQLException occurred while finding a customer: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return result;
	}

	private List<Predicate> buildPredicates(Customer customer) {
	    List<Predicate> predicates = new ArrayList<>();

	    if (customer.getId() != null) {
	        predicates.add(new Predicate("id = ?", customer.getId()));
	    }
	    if (StringUtils.isNotEmpty(customer.getName())) {
	        predicates.add(new Predicate("name like ?", "%" + customer.getName() + "%"));
	    }
	    if (StringUtils.isNotEmpty(customer.getEmail())) {
	        predicates.add(new Predicate("email like ?", "%" + customer.getEmail() + "%"));
	    }
	    if (StringUtils.isNotEmpty(customer.getPhone())) {
	        predicates.add(new Predicate("phone like ?", "%" + customer.getPhone() + "%"));
	    }
	    if (StringUtils.isNotEmpty(customer.getAddress())) {
	        predicates.add(new Predicate("address like ?", "%" + customer.getAddress() + "%"));
	    }

	    return predicates;
	}

	private String buildQueryPredicate(List<Predicate> predicates) {
		String sql = "";
	    if (!predicates.isEmpty()) {
	        sql += " WHERE ";
	        sql += predicates.stream()
	            .map(Predicate::getCondition)
	            .collect(Collectors.joining(" AND "));
	    }

	    return sql;
	}

	private void setSqlParameters(PreparedStatement preparedStatement, List<Predicate> predicates) throws SQLException {
	    for (int i = 0; i < predicates.size(); i++) {
	        preparedStatement.setObject(i + 1, predicates.get(i).getValue());
	    }
	}
	
	Customer toCustomer(ResultSet rs) throws SQLException {
		return Customer.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.phone(rs.getString("phone"))
				.email(rs.getString("email"))
				.address(rs.getString("address"))
				.build();
	}
}
