package entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
	public Order(Customer customer, LocalDateTime orderDate, BigDecimal totalAmount, OrderStatus status) {
		this.customer = customer;
		this.orderDate = orderDate;
		this.status = status;
		this.totalAmount = totalAmount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Customer getCustomer() {
		return customer;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public OrderStatus getStatus() {
		return status;
	}
	
	
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Product product;
	private Order order;

	private Long id;
	private Customer customer;
	private LocalDateTime orderDate;
	private BigDecimal totalAmount;
	private OrderStatus status;
	private List<OrderItem> orderItems;
}
