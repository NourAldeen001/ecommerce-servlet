package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import entities.Order;
import entities.OrderItem;
import java.sql.*;
import java.util.List;

import entities.Order;

public class OrderDaoImpl implements OrderDao {
	
	 @Override
	    public long createOrder(Connection con, Order order) throws Exception {
	        String sql = "INSERT INTO orders (customer_id, status) VALUES (?, ?)";
	        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            ps.setLong(1, order.getCustomerId());
	            ps.setString(2, order.getStatus().name());
	            ps.executeUpdate();
	            try (ResultSet keys = ps.getGeneratedKeys()) {
	                if (keys.next()) return keys.getLong(1);
	                throw new SQLException("فشل إنشاء الطلب");
	            }
	        }
	    }

	    @Override
	    public void addOrderItems(Connection con, List<OrderItem> items) throws Exception {
	        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	            for (OrderItem item : items) {
	                ps.setLong(1, item.getOrderId());
	                ps.setLong(2, item.getProductId());
	                ps.setInt(3, item.getQuatity());
	                ps.setDouble(4, item.getPrice());
	                ps.addBatch();
	            }
	            ps.executeBatch();
	        }
	    }

	    @Override
	    public void updateStatus(Connection con, long orderId, Order.Status status) throws Exception {
	        String sql = "UPDATE orders SET status = ? WHERE id = ?";
	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setString(1, status.name());
	            ps.setLong(2, orderId);
	            ps.executeUpdate();
	        }
	    }

}
