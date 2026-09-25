package dto;

import entities.OrderStatus;

import java.math.BigDecimal;

public class OrderListItemResponse {
	private long id;
	private String customerName;
	private String customerAddress;
	private BigDecimal total;
	private OrderStatus status;
	
	public OrderListItemResponse(long id, String customerName, String customerAddress, BigDecimal total, OrderStatus orderStatus) {
		this.id = id;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.total = total;
		this.status = orderStatus;
	}

	public long getId() {
		return id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public OrderStatus getStatus() {
		return status;
	}
	
	
	
}
