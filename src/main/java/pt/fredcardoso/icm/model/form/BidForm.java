package pt.fredcardoso.icm.model.form;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import pt.fredcardoso.icm.validator.annotation.ProductIdValid;

public class BidForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9141160466214249920L;
	
	@NotNull
	@DecimalMin(value = "0.0")
	private float value;
	@NotNull
	@ProductIdValid
	private int productId;
	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
