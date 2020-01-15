package pt.fredcardoso.icm.services;

import java.util.Date;

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

	public Bid create(BidForm bidForm, User user) {
		Product product = productDao.read(bidForm.getProductId()).get(0);
		
		Bid bid = new Bid();
		
		bid.setDatetime(new Date());
		bid.setValue(bidForm.getValue());
		bid.setUserId(user.getId());
		bid.setProductId(product.getId());
		
		return bidDao.create(bid);
	}
}
