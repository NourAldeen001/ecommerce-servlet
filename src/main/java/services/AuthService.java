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
	
	public void signUp(SignUpRequest request) {
		
		try(Connection connection = connectionFactory.getConnection()) {
			
			connection.setAutoCommit(false);
			
			try {
				UserDao userDao = new UserDao(connection);
				CustomerDao customerDao = new CustomerDao(connection);
				
			
				if(userDao.existsByUsername(request.getUsername())) {
					throw new UsernameAlreadyExistsException();
				}
					
				User user = new User(request.getUsername(), request.getPassword());
				User newUser = userDao.save(user);
				
				System.out.println("ID: " + user.getId() + ", Usename:" + user.getUsername());
			
				if(customerDao.existsByEmail(request.getEmail())) {
					throw new EmailAlreadyExistsException();
				}
				
				Customer customer = new Customer(request.getName(), request.getEmail(),
						request.getPhone(), request.getAddress(), newUser);
				customerDao.save(customer);
				
				connection.commit();
				
			}
			catch (Exception ex) {
				connection.rollback();
				throw ex;
			}
		}
		catch (SQLException ex) {
			throw new DataAccessException("Registeration Failed", ex);
		}	
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
