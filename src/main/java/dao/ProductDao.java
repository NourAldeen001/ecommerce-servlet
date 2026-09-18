package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Product;

public class ProductDao implements ProductDaoInterface {

	@Override
	public List<Product> findAllProducts(ConnectionFactory connectionFactory) {
		ArrayList<Product> products=new ArrayList<>();
		try {
			Connection connection=connectionFactory.getConnection();
			String query="select * from products";
		PreparedStatement statment=connection.prepareStatement(query);
		ResultSet set=statment.executeQuery();
		
		while(set.next()) {
			Product product=new Product();
			product.setId(set.getInt(1));
			product.setName(set.getString(3));
			product.setCategoryId(set.getInt(2));
			product.setDescription(set.getString(4));
			product.setPrice(set.getDouble(5));
			product.setStock(set.getInt(6));
			product.setImage(set.getBytes(7));
			products.add(product);
		}
		
		}catch(SQLException exception) {
			exception.printStackTrace();
		}
		
		return products;
	}

	@Override
	public List<Product> findProductByName(String name,ConnectionFactory connectionFactory) {
		List<Product> products=new ArrayList<>();
		
		try {
			Connection connection=connectionFactory.getConnection();
			String query="select * from products where name like ?";
			PreparedStatement statment=connection.prepareStatement(query);
			statment.setString(1, "%"+name+"%");
			ResultSet set=statment.executeQuery();
			
			while(set.next()) {
				Product product=new Product();
				product.setId(set.getInt(1));
				product.setName(set.getString(3));
				product.setCategoryId(set.getInt(2));
				product.setDescription(set.getString(4));
				product.setPrice(set.getDouble(5));
				product.setStock(set.getInt(6));
				product.setImage(set.getBytes(7));
				products.add(product);
			}
		}catch(SQLException exception) {
			exception.printStackTrace();
		}
		
		
		return products;
	}

	@Override
	public List<Product> findProductsByCategory(int categoryId,ConnectionFactory connectionFactory) {
		List<Product> products=new ArrayList<>();
		
		try {
			Connection connection=connectionFactory.getConnection();
			String query="select * from products where category_id=?";
		PreparedStatement statment=connection.prepareStatement(query);
		statment.setInt(1, categoryId);
		ResultSet set=statment.executeQuery();
		while(set.next()) {
			Product product=new Product();
			product.setId(set.getInt(1));
			product.setName(set.getString(3));
			product.setCategoryId(set.getInt(2));
			product.setDescription(set.getString(4));
			product.setPrice(set.getDouble(5));
			product.setStock(set.getInt(6));
			product.setImage(set.getBytes(7));
			products.add(product);
		}
		
		}catch(SQLException exception) {
			
		}
		
		return products;
	}

	@Override
	public Product getProductDetails(int productId,ConnectionFactory connectionFactory) {
		Product products=new Product();
		try {
			Connection connection=connectionFactory.getConnection();
			String query="select * from products where id=?";
		PreparedStatement statment=connection.prepareStatement(query);
		statment.setInt(1, productId);
		ResultSet set=statment.executeQuery();
		while(set.next()) {
			
			products.setId(set.getInt(1));
			products.setName(set.getString(3));
			products.setCategoryId(set.getInt(2));
			products.setDescription(set.getString(4));
			products.setPrice(set.getDouble(5));
			products.setStock(set.getInt(6));
			products.setImage(set.getBytes(7));
			
		}
		
		}catch(SQLException exception) {
			
		}
		
		return products;
	}
	
	public Product findProductById(int id, ConnectionFactory connectionFactory) {
		Product product=new Product();
		try {
			Connection connection=connectionFactory.getConnection();
			String query="select * from products where id= ?";
			PreparedStatement statment=connection.prepareStatement(query);
			statment.setInt(1, id);
			ResultSet set=statment.executeQuery();
			while(set.next()) {
				
				product.setId(set.getInt(1));
				product.setName(set.getString(3));
				product.setCategoryId(set.getInt(2));
				product.setDescription(set.getString(4));
				product.setPrice(set.getDouble(5));
				product.setStock(set.getInt(6));
				product.setImage(set.getBytes(7));
				
			}
			
		}catch(SQLException exception) {
			exception.printStackTrace();
		}
		
		
		return product;
	}

}
