package pt.fredcardoso.icm.services;

import pt.fredcardoso.icm.exceptions.EmailExistsException;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.LoginForm;
import pt.fredcardoso.icm.model.form.RegisterForm;

public interface UserService {

	public User register(RegisterForm register) throws EmailExistsException;
	
	public boolean validateUser(LoginForm login);
	
}
