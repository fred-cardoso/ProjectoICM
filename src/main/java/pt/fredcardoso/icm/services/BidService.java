package pt.fredcardoso.icm.services;

import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.BidForm;

public interface BidService {

	public Bid create(BidForm bidForm, User user);
	
}
