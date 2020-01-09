package pt.fredcardoso.icm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;

import pt.fredcardoso.icm.dao.UserDAO;
import pt.fredcardoso.icm.exceptions.EmailExistsException;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.LoginForm;
import pt.fredcardoso.icm.model.form.RegisterForm;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	//@Transactional
	public User register(RegisterForm register) throws EmailExistsException {

		if (emailExist(register.getEmail())) {
			throw new EmailExistsException("There is an account with that email address: " + register.getEmail());
		}

		User user = new User();
		user.setEmail(register.getEmail());
		user.setName(register.getName());
		user.setPassword(passwordEncoder.encode(register.getPassword()));

		return userDao.create(user);
	}

	public boolean validateUser(LoginForm login) {
		User user = null;
		
		if(userDao.read(login.getEmail()) == null) {
			return false;
		}
		
		user = userDao.read(login.getEmail()).get(0);
		
		if (passwordEncoder.matches(login.getPassword(), user.getPassword()))
			return true;

		return false;
	}

	private boolean emailExist(String email) {
		List<User> user = userDao.read(email);
		
		if(user == null) {
			return false;
		}
		
		if (user.size() > 0) {
			return true;
		}
		return false;
	}
}
