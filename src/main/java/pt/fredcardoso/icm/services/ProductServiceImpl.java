package pt.fredcardoso.icm.services;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.form.ProductForm;

public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO productDao;

	public Product create(ProductForm productForm) {
		Product product = new Product();
		
		product.setDescription(productForm.getDescription());
		product.setMinimumSalePrice(productForm.getMinimumSalePrice());
		product.setAuctionPeriod(productForm.getAuctionPeriod());
		product.setAwardMode(product.getAwardMode());
		
		return productDao.create(product);
	}
}
