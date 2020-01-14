package pt.fredcardoso.icm.services;

import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.ProductForm;

public interface ProductService {

	public Product create(ProductForm productForm, User user);
	
}
