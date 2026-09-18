package dao;

import java.sql.Connection;
import java.util.List;

import entities.Product;

public interface ProductDaoInterface {
	
	List<Product> findAllProducts(ConnectionFactory connection );
	
	List<Product> findProductByName(String name,ConnectionFactory connection);
	
	List<Product> findProductsByCategory(int id,ConnectionFactory connection);
	
	Product getProductDetails(int productId,ConnectionFactory connection);
	
	Product findProductById(int id, ConnectionFactory connectionFactory);
	
	

}
