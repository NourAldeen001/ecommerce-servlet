package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.DbConnection;
import dao.ProductDao;
import entities.Product;
import java.sql.*;


public class ProductDaoImpl implements ProductDao {
	@Override
	public Product getById(long id) throws Exception {
        String sql = "SELECT id, category_id, name, description, price, stock FROM products WHERE id = ?";

        try(Connection con=DbConnection.getConnection()){
        	PreparedStatement ps =con.prepareStatement(sql);
        	ps.setLong(1, id);
        	try(ResultSet rs=ps.executeQuery()){
        		if(rs.next()) {
        			Product p =new Product();
        			 p.setId(rs.getLong("id"));
                     long catId = rs.getLong("category_id");
                     p.setCategoryId(rs.wasNull() ? null : catId);
                     p.setName(rs.getString("name"));
                     p.setDescription(rs.getString("description"));
                     p.setPrice(rs.getDouble("price"));
                     p.setStock(rs.getInt("stock"));
                     return p;	
        		}
        		return null;
        	}
        }
	};
	 @Override
	    public boolean decreaseStock(long productId, int quantity) throws Exception {
	        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
	        try (Connection con = DbConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, quantity);
	            ps.setLong(2, productId);
	            ps.setInt(3, quantity);
	            return ps.executeUpdate() > 0;
	        }
	    }
	 @Override
	    public void increaseStock(long productId, int quantity) throws Exception {
	        String sql = "UPDATE products SET stock = stock + ? WHERE id = ?";
	        try (Connection con = DbConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, quantity);
	            ps.setLong(2, productId);
	            ps.executeUpdate();
	        }
	    }



}
