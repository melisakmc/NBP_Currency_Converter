package model;

public class CurrencyExchange {
	
	private String quoteCurrency;
	private double exchangeRate;
	private String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getexchangeRate() {
		return exchangeRate;
	}
	public void setexchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getQuoteCurrency() {
		return quoteCurrency;
	}
	public void setQuoteCurrency(String quoteCurrency) {
		this.quoteCurrency = quoteCurrency;
	}
	
	public CurrencyExchange(String quoteCurrency, double exchangeRate, String date) {
		this.quoteCurrency = quoteCurrency;
		this.exchangeRate = exchangeRate;
		this.date = date;
	}
}
