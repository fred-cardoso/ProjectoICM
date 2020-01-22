package pt.fredcardoso.icm.dao;

import java.util.List;

import pt.fredcardoso.icm.model.Sold;

public interface SoldDAO {
	
    public Sold create(Sold sold);
    
    public List<Sold> read(long soldId);
     
    public void update(Sold sold);
     
    public void delete(long soldId);

}
