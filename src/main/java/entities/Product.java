package entities;

public class Product {
	private long id;
	private long categoryId;
	private String name;
	private String description;
	private double price;
	private int stock;
	private byte[] image;
	
	public Product() {}
	
	public Product(long id , long categrotyId,String name , String description, double price,int stock, byte[]image) {
		this.id=id;
		this.categoryId=categrotyId;
		this.name=name;
		this.description=description;
		this.price=price;
		this.stock=stock;
		this.image=image;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}

}
