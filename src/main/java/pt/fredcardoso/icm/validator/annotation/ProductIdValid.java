package pt.fredcardoso.icm.validator.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import pt.fredcardoso.icm.validator.ProductIdValidator;

@Target({ANNOTATION_TYPE,FIELD}) 
@Retention(RUNTIME)
@Constraint(validatedBy = ProductIdValidator.class)
@Documented
public @interface ProductIdValid { 
    String message() default "Product ID not valid";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}