package services;

import dao.ConnectionFactory;
import dao.UserDao;
import dto.AdminResponse;
import dto.CreateAdminRequest;
import dto.DeleteAdminRequest;
import entities.Role;
import entities.User;
import exceptions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminService {

    private ConnectionFactory connFactory;
    public AdminService(ConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    public void addAdmin(CreateAdminRequest request) {
        try(Connection connection = connFactory.getConnection()) {

            connection.setAutoCommit(false);

            try {
                UserDao userDao = new UserDao(connection);

                if(userDao.existsByUsername(request.getUsername())) {
                    throw new UsernameAlreadyExistsException();
                }

                User user = new User(request.getUsername(), request.getPassword());
                user.setRole(Role.ADMIN);
                User newUser = userDao.save(user);

                System.out.println("ID: " + user.getId() + ", Username:" + user.getUsername());

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

    public void deleteAdmin(DeleteAdminRequest request) {
        try(Connection connection = connFactory.getConnection()) {

            connection.setAutoCommit(false);

            try {
                UserDao userDao = new UserDao(connection);
                String usernameToDelete = request.getUsernameToDelete();
                User toDelete = userDao.findByUsername(usernameToDelete)
                        .orElseThrow(() -> new AdminNotFoundException("Could not delete admin user " + usernameToDelete +
                                ". Username not found"));

                if (!(toDelete.getRole() == Role.ADMIN)) {
                    throw new AdminNotFoundException("Could not delete user " + usernameToDelete+ ". Username belongs to a customer not an admin");
                }

                userDao.delete(toDelete.getId());

                System.out.println("User deleted. ID: " + toDelete.getId() + ", Username:" + toDelete.getUsername());

                connection.commit();

            }
            catch (Exception ex) {
                connection.rollback();
                throw ex;
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Failed to delete admin", ex);
        }
    }

    public AdminResponse getById(long id) {
        try (Connection conn = connFactory.getConnection()) {
            UserDao userDao = new UserDao(conn);
            User admin = userDao.findById(id)
                    .orElseThrow(() -> new AdminNotFoundException("Could not find admin user with id: " + id));
            if (admin.getRole() != Role.ADMIN) {
                throw new AdminNotFoundException("User of id: " + id + " is not an admin!");
            }
            return mapToDto(admin);
        } catch (SQLException e) {
            throw new DataAccessException("Database Error. Could not get admin", e);
        }
    }

    public List<AdminResponse> getAll() {
        try (Connection conn = connFactory.getConnection()) {
            UserDao userDao = new UserDao(conn);
            List<User> admins = userDao.findByRole(Role.ADMIN);
            return admins.stream().map(AdminService::mapToDto).toList();
        } catch (SQLException e) {
            throw new DataAccessException("Database Error. Could not get admin", e);
        }
    }

    private static AdminResponse mapToDto(User admin) {
        return new AdminResponse(admin.getId(), admin.getUsername());
    }
}
