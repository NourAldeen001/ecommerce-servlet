package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Optional;

import entities.Customer;
import entities.Role;
import entities.User;
import exceptions.DataAccessException;
import exceptions.DuplicateKeyException;

public class CustomerDao {
	
	private final Connection connection;
	
	public CustomerDao(Connection connection) {	
		if (connection == null) {
			throw new IllegalArgumentException("Database connection cannot be null");
		}
		this.connection = connection;
	}
	
	
	public Customer save(Customer customer) {
		
		String query = "INSERT INTO customers (name, email, phone, address, user_id) VALUES (?, ?, ?, ?, ?)";
		
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			
			statement.setString(1, customer.getName());	
			statement.setString(2, customer.getEmail());
			statement.setString(3, customer.getPhone());
			statement.setString(4, customer.getAddress());
			statement.setLong(5, customer.getUser().getId());
			
			int affectedRows = statement.executeUpdate();
			
			if(affectedRows > 0) {
				try(ResultSet resultSet = statement.getGeneratedKeys()) {
					if(resultSet.next()) {
						long insertedId = resultSet.getLong(1);
						customer.setId(insertedId);
						return customer;
					}
				}
			}
			
		}
		catch(SQLSyntaxErrorException ex) {
			throw new DataAccessException("Invalid SQL syntax", ex);
		}
		catch(SQLIntegrityConstraintViolationException ex) {
			throw new DuplicateKeyException("Email already exists", ex);
		}
		catch(SQLException ex) {
			throw new DataAccessException("Database operation failed when execute save customer", ex);
		}
		return customer;
		
	}
	
	public Optional<Customer> findById(Long id) {
		String query = """ 
						SELECT c.id, c.name, c.email, c.phone, c.address,
						 u.id, u.username, u.role
						FROM customers c
						JOIN users u  
						ON u.id = c.user_id
						WHERE c.id = ?
						""";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) return Optional.of(mapToCustomer(resultSet));
				return Optional.empty();
			}
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute findById", ex);
		}
	}
	
	public Optional<Customer> findByEmail(String email) {
		
		String query = """ 
				SELECT c.id, c.name, c.email, c.phone, c.address,
				 u.id, u.username, u.role
				FROM customers c
				JOIN users u  
				ON u.id = c.user_id
				WHERE c.email = ?
				""";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, email);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) return Optional.of(mapToCustomer(resultSet));
				return Optional.empty();
			}
			
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute findByUsername", ex);
		}
	}
	
	public boolean existsByEmail(String email) {
		
		String query = "SELECT * FROM customers WHERE email = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, email);
			try(ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next();
			}
			
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute existsByUsername", ex);
		}
	}
		
	
	public Customer mapToCustomer(ResultSet resultSet) throws SQLException {
		Customer customer = new Customer();
		customer.setId(resultSet.getLong("id"));
		customer.setName(resultSet.getString("name"));
		customer.setEmail(resultSet.getString("email"));
		customer.setPhone(resultSet.getString("phone"));
		customer.setAddress(resultSet.getString("address"));
		
		
		User user = new User();
		user.setId(resultSet.getLong("user_id"));
		user.setUsername(resultSet.getString("username"));
		user.setRole(Role.valueOf(resultSet.getString("role")));
		customer.setUser(user);
		return customer;
		
	}

}
