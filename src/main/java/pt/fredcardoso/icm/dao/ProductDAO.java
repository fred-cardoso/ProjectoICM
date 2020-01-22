package pt.fredcardoso.icm.dao;

import java.util.List;

import pt.fredcardoso.icm.model.Product;

public interface ProductDAO {
	
    public Product create(Product product);
    
    public List<Product> read(long productId);
     
    public void update(Product product);
     
    public void delete(long productId);

}
