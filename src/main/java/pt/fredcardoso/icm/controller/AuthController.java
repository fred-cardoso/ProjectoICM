package pt.fredcardoso.icm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.fredcardoso.icm.dao.UserDAO;
import pt.fredcardoso.icm.exceptions.EmailExistsException;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.LoginForm;
import pt.fredcardoso.icm.model.form.RegisterForm;
import pt.fredcardoso.icm.services.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDAO userDAO;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("login", new LoginForm());

		return "auth/login.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String postLogin(@Valid @ModelAttribute("login") LoginForm login, BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (userService.validateUser(login)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", userDAO.read(login.getEmail()).get(0));

			return "redirect:/";
		}
		
		redirectAttributes.addFlashAttribute("wrong_credentials", "");

		return "redirect:/login";
	}
	
	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();

		return "redirect:/";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("register", new RegisterForm());

		return "auth/register.html";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("register") RegisterForm register, BindingResult result,
			RedirectAttributes redirectAttributes) {

		User userRegistered = null;

		if (!result.hasErrors()) {
			try {
				userRegistered = userService.register(register);
			} catch (EmailExistsException e) {
			}
		}

		if (userRegistered == null) {
			result.rejectValue("email", "error.email_already_registed");
			return "auth/register.html";
		}

		redirectAttributes.addFlashAttribute("account_creation_success", "");

		return "redirect:/login";
	}
}
