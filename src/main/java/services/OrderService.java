package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ConnectionFactory;
import dao.OrderDao;
import dao.OrderItemsDao;
import dto.*;
import entities.Customer;
import entities.Order;
import entities.OrderItem;
import entities.OrderStatus;
import exceptions.CancelOrderException;
import exceptions.DataAccessException;
import exceptions.OrderNotFoundException;
import exceptions.UncancelOrderException;

public class OrderService {

	private final ConnectionFactory connFactory;
	
	public OrderService(ConnectionFactory connFactory) {
		this.connFactory = connFactory;
	}

	
	public OrderResponse findById(long id) {
		try (Connection connection = connFactory.getConnection()) {
			OrderDao orderDao = new OrderDao(connection);
			OrderItemsDao orderItemsDao = new OrderItemsDao(connection);

			Order order = orderDao.findById(id).orElseThrow(() -> new OrderNotFoundException());
			List<OrderItem> orderItems = orderItemsDao.findByOrderId(id);

			
			
			Customer customer = order.getCustomer();
			CustomerResponse customerResponse = new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getAddress(), customer.getPhone(), null);
			
			
			List<OrderItemResponse> orderItemResponses = orderItems.stream().map(OrderService::mapOrderItem).toList();
			OrderResponse orderDto = new OrderResponse(id, customerResponse, order.getTotalAmount(), order.getStatus(), order.getOrderDate(), orderItemResponses);
			
			return orderDto;
		} catch (SQLException e) {
			throw new DataAccessException("Database Error. Could not find orders", e);
		}
	}

	
	private static OrderItemResponse mapOrderItem(OrderItem orderItem) {
		return new OrderItemResponse(orderItem.getId(), orderItem.getQuantity(), orderItem.getPrice());
	}
	
	
	public List<OrderListItemResponse> findAllOrders() {
		try (Connection connection = connFactory.getConnection()) {
			OrderDao orderDao = new OrderDao(connection);
			List<Order> orders = orderDao.findAll();
			List<OrderListItemResponse> orderDtos = new ArrayList<>();
			for (Order order : orders) {
				Customer customer = order.getCustomer();
				OrderListItemResponse orderLvi = new OrderListItemResponse(order.getId(), customer.getName(), customer.getAddress(), order.getTotalAmount(), order.getStatus());
				orderDtos.add(orderLvi);
			}
			return orderDtos;

		} catch (SQLException e) {
			throw new DataAccessException("Database Error. Could not find orders", e);
		}
		
	}

	
	public void updateStatus(OrderUpdateStatusRequest orderUpdateStatusRequest) {
		try (Connection connection = connFactory.getConnection()) {
			connection.setAutoCommit(false);
			try {
				OrderDao orderDao = new OrderDao(connection);

				Order order = orderDao.findById(orderUpdateStatusRequest.getOrderId())
						.orElseThrow(() -> new OrderNotFoundException());

				if (orderUpdateStatusRequest.getStatus() == OrderStatus.CANCELLED) {
					throw new CancelOrderException();
				} else if (order.getStatus() == OrderStatus.CANCELLED) {
					throw new UncancelOrderException();
				}

				orderDao.updateStatus(orderUpdateStatusRequest.getOrderId(), orderUpdateStatusRequest.getStatus());
				connection.commit();
			} catch (Exception ex) {
				connection.rollback();
				throw ex;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Database Error. Could not update order", e);
		}
	}


	public void cancelOrder(long orderId) {
		try (Connection connection = connFactory.getConnection()) {
			connection.setAutoCommit(false);
			try {
				OrderDao orderDao = new OrderDao(connection);
				Order order = orderDao.findById(orderId)
						.orElseThrow(() -> new OrderNotFoundException());

				if (order.getStatus() == OrderStatus.CANCELLED) {
					throw new CancelOrderException();
				}
				// TODO: issue a refund and restock products
				orderDao.updateStatus(orderId, OrderStatus.CANCELLED);
				connection.commit();
			} catch (Exception ex) {
				connection.rollback();
				throw ex;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Database Error. Could not update order", e);
		}
	}

	public List<OrderListItemResponse> findOrdersByStatus(OrderStatus status) {
		try (Connection connection = connFactory.getConnection()) {
			OrderDao orderDao = new OrderDao(connection);
			List<Order> orders = orderDao.findByStatus(status);
			List<OrderListItemResponse> orderDtos = new ArrayList<>();
			for (Order order: orders) {
				Customer customer = order.getCustomer();
				
				OrderListItemResponse orderLvi = new OrderListItemResponse(order.getId(), customer.getName(), customer.getAddress(), order.getTotalAmount(), order.getStatus());
				
				orderDtos.add(orderLvi);
			}
			return orderDtos;
		} catch (SQLException e) {
			throw new DataAccessException("Database Error. Could not find orders", e);
		}
		
	}
	
	
}
