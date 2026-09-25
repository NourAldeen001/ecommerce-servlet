package dto;

public class CartItemDto {
	private long cartItemId;
	private long productId;
	private String productName;
	private double currentPrice;
	private int quantity;
	private int availableStock;
	private double subtotal;
	
	public CartItemDto(long cartItemId, long productId, String productName,
            double currentPrice, int quantity, int availableStock) {
this.cartItemId = cartItemId;
this.productId = productId;
this.productName = productName;
this.currentPrice = currentPrice;
this.quantity = quantity;
this.availableStock = availableStock;
this.subtotal = currentPrice * quantity;
}

	public long getCartItemId() { return cartItemId; }
    public long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getCurrentPrice() { return currentPrice; }
    public int getQuantity() { return quantity; }
    public int getAvailableStock() { return availableStock; }
    public double getSubtotal() { return subtotal; }
}
