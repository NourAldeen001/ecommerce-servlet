package entities;

import java.sql.Timestamp;

public class Order {
	  public enum Status { PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED };
	  private long id;
	  private long customerId;
	 private  Timestamp orderDate;
	 private Status status;

	    public long getId() {
		return id;
	}
	 public void setId(long id) {
		 this.id = id;
	 }
	 public long getCustomerId() {
		 return customerId;
	 }
	 public void setCustomerId(long customerId) {
		 this.customerId = customerId;
	 }
	 public Timestamp getOrderDate() {
		 return orderDate;
	 }
	 public void setOrderDate(Timestamp orderDate) {
		 this.orderDate = orderDate;
	 }
	 public Status getStatus() {
		 return status;
	 }
	 public void setStatus(Status status) {
		 this.status = status;
	 }
		public Order() {}
	    public Order(long customerId, Status status) {
	        this.customerId = customerId;
	        this.status = status;
	    }

}
