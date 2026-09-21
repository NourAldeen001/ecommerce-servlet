package entities;

public class OrderItem {
	private long id ;
	private long orderId;
	private long productId;
	private int quatity;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getQuatity() {
		return quatity;
	}

	public void setQuatity(int quatity) {
		this.quatity = quatity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	private double price;
	
	public OrderItem() {}
	
	public OrderItem(long orderId,long productId,int quantity,double price) {
		this.orderId=orderId;
		this.productId=productId;
		this.quatity=quantity;
		this.price=price;
		
	}

}
