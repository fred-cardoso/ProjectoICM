package pt.fredcardoso.icm.services;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.ProductForm;

public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO productDao;

	public Product create(ProductForm productForm, User user) {
		Product product = new Product();
		product.setName(productForm.getName());
		product.setDescription(productForm.getDescription());
		product.setMinimumSalePrice(productForm.getMinimumSalePrice());
		product.setAuctionPeriod(productForm.getAuctionPeriod());
		product.setAwardMode(productForm.getAwardMode());
		product.setUser(user);
		
		return productDao.create(product);
	}
}
