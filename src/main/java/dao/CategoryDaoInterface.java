package dao;

import java.sql.Connection;
import java.util.List;

import dto.Category;

public interface CategoryDaoInterface {
	
	List<Category> getAllCategories(ConnectionFactory connection);

}
