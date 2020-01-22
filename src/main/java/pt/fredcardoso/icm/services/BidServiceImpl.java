package pt.fredcardoso.icm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.BidDAO;
import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.BidForm;

public class BidServiceImpl implements BidService {
	
	@Autowired
	private BidDAO bidDao;
	
	@Autowired
	private ProductDAO productDao;
	
	@Autowired
	private ProductService productService;

	public Bid create(BidForm bidForm, User user) {
		Product product = productDao.read(bidForm.getProductId()).get(0);
		
		if(productService.getLatestBidValue(product.getId()) > bidForm.getValue()) {
			return null;
		}
		
		Bid bid = new Bid();
		
		bid.setDatetime(new Date());
		bid.setValue(bidForm.getValue());
		bid.setUserId(user.getId());
		bid.setProductId(product.getId());
		
		return bidDao.create(bid);
	}
	
	public List<Bid> getBidFromProductId(long productId) {
		List<Bid> bids = bidDao.read(0);
		List<Bid> result = new ArrayList<Bid>();
		
		if(bids == null) {
			return result;
		}
		
		for (Bid bid : bids) {
			if (bid.getProductId() == productId) {
				result.add(bid);
			}
		}
		
		return result;
	}
}
