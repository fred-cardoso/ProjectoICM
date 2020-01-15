package pt.fredcardoso.icm.dao;

import java.util.List;

import pt.fredcardoso.icm.model.Bid;

public interface BidDAO {
	
    public Bid create(Bid bid);
    
    public List<Bid> read(long bidId);
     
    public void update(Bid bid);
     
    public void delete(long bidId);

}
