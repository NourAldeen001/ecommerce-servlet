package dao;

import java.sql.Connection;
import java.util.List;

import entities.OrderItem;

import entities.Order;

public interface OrderDao {
	long createOrder(Connection con, Order order) throws Exception;
    void addOrderItems(Connection con, List<OrderItem> items) throws Exception;
    void updateStatus(Connection con, long orderId, Order.Status status) throws Exception;
}
