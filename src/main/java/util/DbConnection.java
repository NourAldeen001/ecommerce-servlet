package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	private static final String URL="jdbc:mysql://localhost:3306/ecommerce_db";
	private static final String PASS="1111";
	private static final String USER="root";
	
	   public static Connection getConnection() throws SQLException {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            return DriverManager.getConnection(URL, USER, PASS);
	        } catch (ClassNotFoundException e) {
	            throw new SQLException("MySQL Driver not found", e);
	        }
	   }}
