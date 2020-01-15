package pt.fredcardoso.icm.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.form.BidForm;
import pt.fredcardoso.icm.validator.annotation.PasswordMatches;
import pt.fredcardoso.icm.validator.annotation.ProductIdValid;

public class ProductIdValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Autowired
	private ProductDAO productDao;

	public void initialize(ProductIdValid constraintAnnotation) {
	}

	public boolean isValid(Object obj, ConstraintValidatorContext context){
      BidForm bid = (BidForm) obj;
      
      if(productDao.read(bid.getProductId()) == null) {
    	  return false;
      }
      
      return true;  
  }
}