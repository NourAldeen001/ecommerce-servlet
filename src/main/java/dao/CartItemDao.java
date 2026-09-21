package dao;

import java.util.List;

import entities.CartItem;

public interface CartItemDao {
	void add(CartItem item) throws Exception;
    CartItem findByCustomerAndProduct(long customerId, long productId) throws Exception;
    List<CartItem> findByCustomerId(long customerId) throws Exception;
    void updateQuantity(long cartItemId, int quantity) throws Exception;
    void delete(long cartItemId) throws Exception;
    void clearByCustomerId(long customerId) throws Exception;

}
