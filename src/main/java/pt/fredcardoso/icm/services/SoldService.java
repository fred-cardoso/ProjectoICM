package pt.fredcardoso.icm.services;

import pt.fredcardoso.icm.model.Sold;

public interface SoldService {

	public boolean checkSoldFromProductId(long productId);
	
	public Sold sellProductAfterAuctionPeriod(long productId);
	
}
