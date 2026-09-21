package dto;

public class OrderResponseDto {

	private long orderId;
    private double totalAmount;
    private String status;
    private String message;
    
    public OrderResponseDto(long orderId, double totalAmount, String status, String message) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.message = message;
    }

    public long getOrderId() { return orderId; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getMessage() { return message; }
}
