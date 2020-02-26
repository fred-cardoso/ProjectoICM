package pt.fredcardoso.icm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.fredcardoso.icm.dao.UserDAO;
import pt.fredcardoso.icm.model.User;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserDAO userDao;

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public String users(Model model) {
		
		List<User> users = userDao.read(-1);
		
		model.addAttribute("users", users);
		
		return "users/index.html";
	}
	
	@RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
	public String user(@PathVariable(value="id") int id, Model model) {
		
		List<User> user = userDao.read(id);
		
		model.addAttribute("users", user);
		
		return "users/user.html";
	}
	
	//@Transactional
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		
		userDao.update(user);

		redirectAttributes.addFlashAttribute("message", "User updated sucessfully!");

		return "redirect:/users";
	}
	
	//@Transactional
	@RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") int id, Model model, RedirectAttributes redirectAttributes) {
		
		List<User> user = userDao.read(id);
		
		if(user == null) {
			return "redirect:/users";
		}
		
		userDao.delete(id);
		
		redirectAttributes.addFlashAttribute("message", "User deleted sucessfully!");
		
		return "redirect:/users";
	}
}