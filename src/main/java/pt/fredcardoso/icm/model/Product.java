package pt.fredcardoso.icm.model;

import java.util.Date;
import java.util.List;

public class Product {

	public static final String MINIMUM_VALUE = "minValue";
	public static final String AUCTION_PERIOD = "auctionPeriod";

	private int id;
	private String description;
	private double minimumSalePrice;
	private Date auctionPeriod;
	private String awardMode;
	private String name;
	private User user;
	private List<Multimedia> multimedia;
	private List<Bid> bids;
	private boolean isSold;

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

	public String  getName() {
		return name;
	}
	public void setName (String name) {
		this.name = name;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Multimedia> getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(List<Multimedia> multimedia) {
		this.multimedia = multimedia;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
	
	public boolean isSold() {
		return isSold;
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}

	@Override
	public String toString() {
		return "Produto com ID: " + id + ", descrição: " + description + ", valor de venda minimo: " + minimumSalePrice
				+ ", periodo de leilao: " + auctionPeriod + " e modo de premio: " + awardMode;
	}
}
