package pt.fredcardoso.icm.model.form;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class ProductForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6446571164869522285L;

	@NotNull
	@NotBlank
	private String description;
	@NotNull
	@DecimalMin(value = "0.0")
	private double minimumSalePrice;
	@NotNull
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date auctionPeriod;
	@NotNull
	@NotBlank
	private String awardMode;
	@NotNull
	@NotBlank 
	private String name;
	private List<File> multimedia;
	
	
	public List<File> getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(List<File> multimedia) {
		this.multimedia = multimedia;
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
	public double getMinimumSalePrice() {
		return minimumSalePrice;
	}
	public void setMinimumSalePrice(double minimumSalePrice) {
		this.minimumSalePrice = minimumSalePrice;
	}
	public Date getAuctionPeriod() {
		return auctionPeriod;
	}
	public void setAuctionPeriod(String auctionPeriod) {
		SimpleDateFormat dataFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			this.auctionPeriod = dataFormat.parse(auctionPeriod);
		} catch (ParseException e) {
			this.auctionPeriod = null;
		}
	}
	public String getAwardMode() {
		return awardMode;
	}
	public void setAwardMode(String awardMode) {
		this.awardMode = awardMode;
	}
}
