package services;

import java.sql.Connection;
import java.util.List;

import dao.CategoryDao;
import dao.ConnectionFactory;
import dto.Category;

public class CategoryService {
	
	private CategoryDao category;
	public CategoryService() {
		category=new CategoryDao();
	}
	
	public List<Category> getCategories(ConnectionFactory connection){
		return category.getAllCategories(connection);
	}

}
