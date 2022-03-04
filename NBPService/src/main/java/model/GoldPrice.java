package model;

public class GoldPrice {

	private double price;
	private String date;
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public GoldPrice(String date, double price) {
		this.date = date;
		this.price = price;
	}
}
