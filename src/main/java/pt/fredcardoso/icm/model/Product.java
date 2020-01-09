package pt.fredcardoso.icm.model;

import java.util.Date;

public class Product {

	public static final String MINIMUM_VALUE = "minValue";
	public static final String AUCTION_PERIOD = "auctionPeriod";

	private int id;
	private String description;
	private double minimumSalePrice;
	private Date auctionPeriod;
	private String awardMode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMinimumSalePrice() {
		return minimumSalePrice;
	}

	public void setMinimumSalePrice(double minimumSaleValue) {
		this.minimumSalePrice = minimumSaleValue;
	}

	public Date getAuctionPeriod() {
		return auctionPeriod;
	}

	public void setAuctionPeriod(Date auctionPeriod) {
		this.auctionPeriod = auctionPeriod;
	}

	public String getAwardMode() {
		return awardMode;
	}

	public void setAwardMode(String awardMode) {
		this.awardMode = awardMode;
	}

	@Override
	public String toString() {
		return "Produto com ID: " + id + ", descrição: " + description + ", valor de venda minimo: " + minimumSalePrice
				+ ", periodo de leilao: " + auctionPeriod + " e modo de premio: " + awardMode;
	}
}
