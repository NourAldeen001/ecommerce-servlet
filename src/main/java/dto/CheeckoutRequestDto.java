package dto;

public class CheeckoutRequestDto {
	private long customerId;

	private String paymentMethod;
	  public long getCustomerId() { return customerId; }
	    public void setCustomerId(long customerId) { this.customerId = customerId; }
	    public String getPaymentMethod() { return paymentMethod; }
	    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }


}
