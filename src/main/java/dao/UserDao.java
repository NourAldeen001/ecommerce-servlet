package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Optional;

import entities.Role;
import entities.User;
import exceptions.DataAccessException;
import exceptions.DuplicateKeyException;

public class UserDao {
	
	private final Connection connection;
	
	public UserDao(Connection connection) {	
		if (connection == null) {
			throw new IllegalArgumentException("Database connection cannot be null");
		}
		this.connection = connection;
	}
	
	
	public User save(User user) {
		
		String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
		
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			
			statement.setString(1, user.getUsername());	
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getRole().name());
			int affectedRows = statement.executeUpdate();
			
			if(affectedRows > 0) {
				try(ResultSet resultSet = statement.getGeneratedKeys()) {
					if(resultSet.next()) {
						long insertedId = resultSet.getLong(1);
						user.setId(insertedId);
						return user;
					}
				}
			}
		}
		catch(SQLSyntaxErrorException ex) {
			throw new DataAccessException("Invalid SQL syntax", ex);
		}
		catch(SQLIntegrityConstraintViolationException ex) {
			throw new DuplicateKeyException("Username already exists", ex);
		}
		catch(SQLException ex) {
			throw new DataAccessException("Database operation failed when execute save user", ex);
		}
		return user;
		
	}
	
	public Optional<User> findById(Long id) {
		String query = "SELECT id, username, password, role FROM users WHERE id = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) return Optional.of(mapToUser(resultSet));
				return Optional.empty();
			}
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute findById", ex);
		}
	}
	
	public Optional<User> findByUsername(String username) {
		
		String query = "SELECT id, username, password, role FROM users WHERE username = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, username);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) return Optional.of(mapToUser(resultSet));
				return Optional.empty();
			}
			
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute findByUsername", ex);
		}
	}
	
	public boolean existsByUsername(String username) {
		
		String query = "SELECT * FROM users WHERE username = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, username);
			try(ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next();
			}
			
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute existsByUsername", ex);
		}
	}
		
	
	public User mapToUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setRole(Role.valueOf(resultSet.getString("role")));
		return user;
		
	}

}
