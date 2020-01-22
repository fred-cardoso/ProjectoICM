package pt.fredcardoso.icm.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.validator.annotation.ProductIdValid;

public class ProductIdValidator implements ConstraintValidator<ProductIdValid, Object> {

	@Autowired
	private ProductDAO productDao;

	public void initialize(ProductIdValid constraintAnnotation) {
	}

	public boolean isValid(Object obj, ConstraintValidatorContext context){
      int productId = (Integer) obj;
      
      if(productDao.read(productId) == null) {
    	  return false;
      }
      
      return true;  
  }
}