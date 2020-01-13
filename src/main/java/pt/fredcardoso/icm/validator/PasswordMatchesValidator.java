package pt.fredcardoso.icm.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pt.fredcardoso.icm.model.form.RegisterForm;
import pt.fredcardoso.icm.validator.annotation.PasswordMatches;

public class PasswordMatchesValidator 
implements ConstraintValidator<PasswordMatches, Object> {
   
  public void initialize(PasswordMatches constraintAnnotation) {       
  }
  public boolean isValid(Object obj, ConstraintValidatorContext context){
      RegisterForm user = (RegisterForm) obj;
      return user.getPassword().equals(user.getPasswordConfirmation());    
  }     
}