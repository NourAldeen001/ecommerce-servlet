package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Order;
import entities.OrderItem;
import exceptions.DataAccessException;

public class OrderItemsDao {
	private Connection connection;
	public OrderItemsDao(Connection connection) {
		this.connection = connection;
	}


	public List<OrderItem> findByOrderId(long orderId) {
		String query = """
				SELECT oi.id,
				oi.quantity, 
				oi.price,
				oi.order_id,
				oi.product_id
				from order_items oi
				where order_id = ?
				""";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, orderId);
			
			try (ResultSet rs = ps.executeQuery()) {
				return mapToList(rs);
			}
		} catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute findByOrderId", ex);
		}
	}


	public OrderItem save(OrderItem orderItem) {
		String query = "INSERT INTO order_items (product_id, order_id, price, quantity) values (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setLong(1, orderItem.getProductId());
			ps.setLong(2, orderItem.getOrderId());
			ps.setBigDecimal(3, orderItem.getPrice());
			ps.setInt(4, orderItem.getQuantity());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					long id = rs.getLong(1);
					orderItem.setId(id);
				}
			}
			return orderItem;
		} catch (SQLIntegrityConstraintViolationException ex) {
			throw new DataAccessException("Invalid inserted order item ", ex);
		} catch(SQLSyntaxErrorException ex) {
			throw new DataAccessException("Invalid SQL Syntax ", ex);

		} catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute insertOrder", ex);
		}
	}


	protected OrderItem mapToOrderItem(ResultSet rs) throws SQLException {
		long id = rs.getLong("id");
		int quantity = rs.getInt("quantity");
		BigDecimal price = rs.getBigDecimal("price");
		long orderId = rs.getLong("order_id");
		long productId = rs.getLong("product_id");

		OrderItem orderItem = new OrderItem(quantity, price, orderId, productId);
		orderItem.setId(id);
		return orderItem;
	}
	
	protected List<OrderItem> mapToList(ResultSet rs) throws SQLException {
		List<OrderItem> orderItems = new ArrayList<>();
		while(rs.next()) {
			orderItems.add(mapToOrderItem(rs));
		}
		return orderItems;
	}
	
}
