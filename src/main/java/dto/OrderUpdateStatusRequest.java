package dto;

import entities.OrderStatus;

public class OrderUpdateStatusRequest {
	private long orderId;
	private OrderStatus status;

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public void setStatus(String status) {
		this.status = OrderStatus.valueOf(status);
	}

	public OrderUpdateStatusRequest(long orderId, String status) {
		setOrderId(orderId);
		setStatus(status);
	}
	
	
	public long getOrderId() {
		return orderId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	
	
}
