package dao;
import util.DbConnection;
import entities.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartitemDaoImpl implements CartItemDao {
	
	@Override
    public void add(CartItem item) throws Exception {
        String sql = "INSERT INTO cart_items (customer_id, product_id, quantity) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, item.getCustomerId());
            ps.setLong(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.executeUpdate();
        }
    }

    @Override
    public CartItem findByCustomerAndProduct(long customerId, long productId) throws Exception {
        String sql = "SELECT id, customer_id, product_id, quantity FROM cart_items WHERE customer_id = ? AND product_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, customerId);
            ps.setLong(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                return null;
            }
        }
    }

    @Override
    public List<CartItem> findByCustomerId(long customerId) throws Exception {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT id, customer_id, product_id, quantity FROM cart_items WHERE customer_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public void updateQuantity(long cartItemId, int quantity) throws Exception {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setLong(2, cartItemId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(long cartItemId) throws Exception {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, cartItemId);
            ps.executeUpdate();
        }
    }

    @Override
    public void clearByCustomerId(long customerId) throws Exception {
        String sql = "DELETE FROM cart_items WHERE customer_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, customerId);
            ps.executeUpdate();
        }
    }

    private CartItem mapRow(ResultSet rs) throws SQLException {
        CartItem item = new CartItem();
        item.setId(rs.getLong("id"));
        item.setCustomerId(rs.getLong("customer_id"));
        item.setProductId(rs.getLong("product_id"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }
	
	

}
