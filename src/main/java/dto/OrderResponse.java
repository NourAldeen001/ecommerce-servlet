package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import entities.OrderStatus;

public class OrderResponse {
	
	public long getId() {
		return id;
	}
	public CustomerResponse getCustomer() {
		return customer;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public List<OrderItemResponse> getOrderItems() {
		return orderItems;
	}
	public OrderResponse(long id, CustomerResponse customer, BigDecimal totalAmount, OrderStatus status, LocalDateTime date,
						 List<OrderItemResponse> orderItems) {
		this.id = id;
		this.customer = customer;
		this.totalAmount = totalAmount;
		this.status = status;
		this.date = date;
		this.orderItems = orderItems;
	}
	
	
	private long id;
	private CustomerResponse customer;
	private BigDecimal totalAmount;
	private OrderStatus status;
	private LocalDateTime date;
	private List<OrderItemResponse> orderItems;

}
