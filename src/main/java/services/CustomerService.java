package services;

import dao.ConnectionFactory;
import dao.CustomerDao;
import dao.UserDao;
import dto.CustomerResponse;
import dto.SignUpRequest;
import entities.*;
import exceptions.CustomerNotFoundException;
import exceptions.DataAccessException;
import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerService {

    private final ConnectionFactory connFactory;

    public CustomerService(ConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    public List<CustomerResponse> findAll() {
        try (Connection connection = connFactory.getConnection()) {
            CustomerDao customerDao = new CustomerDao(connection);
            List<Customer> customers = customerDao.findAll();
            List<CustomerResponse> result = customers.stream().map(this::mapToDto).toList();
            return result;
        } catch (SQLException e) {
            throw new DataAccessException("Database Error. Could not get customers", e);
        }
    }

    public CustomerResponse findById(long id) {
        try (Connection connection = connFactory.getConnection()) {
            CustomerDao customerDao = new CustomerDao(connection);
            Customer customer = customerDao.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException());
            return mapToDto(customer);
        } catch (SQLException e) {
            throw new DataAccessException("Database Error. Could not get customer", e);
        }
    }

        private CustomerResponse mapToDto(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getUser().getUsername());
        return customerResponse;
    }

    public void signUp(SignUpRequest request) {

        try(Connection connection = connFactory.getConnection()) {

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
            throw new DataAccessException("Registration Failed", ex);
        }
    }
}
