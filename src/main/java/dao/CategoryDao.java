package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Category;

public class CategoryDao implements CategoryDaoInterface{

	@Override
	public List<Category> getAllCategories(ConnectionFactory connectionFactory) {
		List<Category> categories=new ArrayList<>();
		try {
			Connection connection=connectionFactory.getConnection();
			String query="select * from categories";
			PreparedStatement statment=connection.prepareStatement(query);
			ResultSet set=statment.executeQuery();
			while(set.next()) {
				Category category=new Category();
				category.setId(set.getInt(1));
				category.setName(set.getString(2));
				categories.add(category);
			}
		}catch(SQLException sql) {
			sql.printStackTrace();
		}
		
		return categories;
	}
	

}
