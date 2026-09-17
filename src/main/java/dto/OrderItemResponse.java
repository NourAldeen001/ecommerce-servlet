package dto;

import java.math.BigDecimal;

public class OrderItemResponse {
	
	public long getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	private long id;
	private int quantity;
	private BigDecimal price;
	
	public OrderItemResponse(long productId, int quantity, BigDecimal price) {
		this.quantity = quantity;
		this.price = price;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	

}
