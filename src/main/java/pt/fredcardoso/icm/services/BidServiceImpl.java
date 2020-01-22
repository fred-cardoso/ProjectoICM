package pt.fredcardoso.icm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.BidDAO;
import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.dao.SoldDAO;
import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.Sold;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.BidForm;

public class BidServiceImpl implements BidService {

	@Autowired
	private BidDAO bidDao;

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private SoldDAO soldDao;

	public Bid create(BidForm bidForm, User user) {
		Product product = productDao.read(bidForm.getProductId()).get(0);

		if (productService.getLatestBidValue(product.getId()) > bidForm.getValue() || product.isSold()) {
			return null;
		}

		Bid bid = new Bid();

		bid.setDatetime(new Date());
		bid.setValue(bidForm.getValue());
		bid.setUserId(user.getId());
		bid.setProductId(product.getId());

		Bid result = bidDao.create(bid);

		if (result == null) {
			return result;
		}

		if (product.getAwardMode().contentEquals(Product.MINIMUM_VALUE) && bidForm.getValue() > product.getMinimumSalePrice()) {
			Sold sold = new Sold();
			sold.setBuyerId(user.getId());
			sold.setProductId(product.getId());
			sold.setSellerId(product.getUser().getId());
			soldDao.create(sold);
		}

		return result;
	}

	public List<Bid> getBidFromProduct(long productId) {
		List<Bid> bids = bidDao.read(-1);
		List<Bid> result = new ArrayList<Bid>();

		if (bids == null) {
			return result;
		}

		for (Bid bid : bids) {
			if (bid.getProductId() == productId) {
				result.add(bid);
			}
		}

		return result;
	}

	public Bid getLatestBid(long productId) {
		List<Bid> bids = this.getBidFromProduct(productId);
		Bid bid = null;
		
		for(Bid b : bids) {
			if(bid == null) {
				bid = b;
				continue;
			}
			
			if(bid.getDatetime().before(b.getDatetime())) {
				bid = b;
			}
		}
		
		return bid;
	}
}
