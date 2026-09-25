package services;

import java.sql.Connection;
import java.util.List;

import dao.ConnectionFactory;
import dao.ProductDao;
import entities.Product;

public class ProductService {
	
	private ProductDao productDao;
	
	public ProductService() {
		productDao=new ProductDao();
	}
	
	public List<Product> findAllProducts(ConnectionFactory connection){
		return productDao.findAllProducts(connection);
	}
	
	public List<Product> findProductByName(String name,ConnectionFactory connection){
		return productDao.findProductByName(name, connection);
	}
	
	public List<Product> findProductsByCategory(int categoryId,ConnectionFactory connection){
		return productDao.findProductsByCategory(categoryId, connection);
	}
	
	public Product getProductDetails(int productId,ConnectionFactory connection){
		return productDao.getProductDetails(productId, connection);
	}
	
	public Product findProductById(int productId,ConnectionFactory connection){
		return productDao.findProductById(productId, connection);
	}

}

