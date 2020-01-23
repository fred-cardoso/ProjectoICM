package pt.fredcardoso.icm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.dao.SoldDAO;
import pt.fredcardoso.icm.dao.UserDAO;
import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.Sold;
import pt.fredcardoso.icm.model.User;

public class SoldServiceImpl implements SoldService {
	
	@Autowired
	private SoldDAO soldDao;
	
	@Autowired
	private ProductDAO productDao;
	
	@Autowired
	private BidService bidSerivce;
	
	@Autowired
	private UserDAO userDao;

	public boolean checkSoldFromProductId(long productId) {
		List<Sold> solds = soldDao.read(-1);
		
		if(solds == null) {
			return false;
		}
		
		for (Sold sold : solds) {
			if(sold.getProductId() == productId) {
				return true;
			}
		}
		
		return false;
	}

	public Sold sellProductAfterAuctionPeriod(long productId) {
		List<Product> products = productDao.read(productId);
		
		if(products == null) {
			return null;
		}
		
		Product p = products.get(0);
		User seller = p.getUser();
		
		Bid latestBid = bidSerivce.getLatestBid(productId);
		
		if(latestBid == null) {
			return null;
		}
		
		User buyer = userDao.read((int) latestBid.getUserId()).get(0);
		
		Sold sold = new Sold();
		sold.setBuyerId(buyer.getId());
		sold.setProductId(p.getId());
		sold.setSellerId(seller.getId());
		
		soldDao.create(sold);
		
		return null;
	}
}
