package pt.fredcardoso.icm.model.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import pt.fredcardoso.icm.validator.annotation.PasswordMatches;

@PasswordMatches
public class RegisterForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4364005272327316211L;

	@NotNull
	@NotBlank
	private String name;
	
	@Email
	@NotNull
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	@NotBlank
	private String passwordConfirmation;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	
	@Override
	public String toString() {
		return "Registo com nome: " + name + " email: " + email + " password: " + password + " e confirma��o de password: " + passwordConfirmation;
	}
	
}
