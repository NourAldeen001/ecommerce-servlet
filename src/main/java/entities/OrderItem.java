package entities;

import java.math.BigDecimal;

public class OrderItem {
	public OrderItem(int quantity, BigDecimal price, long orderId, long productId) {
		this.quantity = quantity;
		this.price = price;
		this.orderId = orderId;
		this.productId = productId;
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
	private long productId;
	private long orderId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
