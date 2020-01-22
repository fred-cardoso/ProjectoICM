package pt.fredcardoso.icm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.ProductForm;

public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private MultimediaService multimediaService;

	public Product create(ProductForm productForm, User user) {
		Product product = new Product();
		product.setName(productForm.getName());
		product.setDescription(productForm.getDescription());
		product.setMinimumSalePrice(productForm.getMinimumSalePrice());
		product.setAuctionPeriod(productForm.getAuctionPeriod());
		product.setAwardMode(productForm.getAwardMode());
		product.setUser(user);
		Product insertedProduct = productDao.create(product);

		multimediaService.create(insertedProduct.getId(), productForm.getMultimedia());

		return insertedProduct;
	}

	public float getLatestBid(long productId) {
		List<Product> products = productDao.read(productId);
		float result = 0f;
		
		if (products == null) {
			return 0;
		}

		Product product = products.get(0);

		List<Bid> bids = product.getBids();
		
		if (bids == null) {
			return 0;
		}

		for(Bid bid : bids) {
			if(bid.getValue() > result) {
				result = bid.getValue();
			}
		}
		
		return result;

	}
}
