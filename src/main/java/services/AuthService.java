package services;

import java.sql.Connection;
import java.sql.SQLException;

import dao.ConnectionFactory;
import dao.CustomerDao;
import dao.UserDao;
import dto.LoginRequest;
import dto.SignUpRequest;
import entities.Customer;
import entities.User;
import exceptions.DataAccessException;
import exceptions.EmailAlreadyExistsException;
import exceptions.AuthenticationException;
import exceptions.UsernameAlreadyExistsException;


public class AuthService {
	
	private final ConnectionFactory connectionFactory;
	
	public AuthService(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public User login(LoginRequest request) {
		User user = null;
		try(Connection connection = connectionFactory.getConnection()) {
		
			UserDao userDao = new UserDao(connection);
		
			user = userDao.findByUsername(request.getUsername())
					.orElseThrow(() -> new AuthenticationException());
				
			if(!user.getPassword().equals(request.getPassword())) 
				throw new AuthenticationException();
		
		}
		catch (SQLException ex) {
			throw new DataAccessException("Login Failed", ex);
		}
		return user;

	}
}
