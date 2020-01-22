package pt.fredcardoso.icm.services;

import java.util.List;

import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.BidForm;

public interface BidService {

	public Bid create(BidForm bidForm, User user);
	
	public List<Bid> getBidFromProductId(long productId);
	
}
