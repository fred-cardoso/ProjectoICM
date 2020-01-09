
package pt.fredcardoso.icm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.form.ProductForm;
import pt.fredcardoso.icm.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String products(Model model) {

		List<Product> products = productDao.read(-1);

		model.addAttribute("products", products);
		
		System.out.println(products);

		return "products/index.html";
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public String product(@PathVariable(value = "id") int id, Model model) {

		List<Product> user = productDao.read(id);

		model.addAttribute("users", user);

		return "users/user.html";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("product", new ProductForm());

		return "products/create.html";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("product") ProductForm product, BindingResult result,
			RedirectAttributes redirectAttributes) {

		Product productCreated = null;

		if (!result.hasErrors()) {
			productCreated = productService.create(product);
		}

		if (productCreated == null) {
			System.out.println(result.getAllErrors());
			//result.rejectValue("error", "Unknown error ocurred.");
			return "products/create.html";
		}

		redirectAttributes.addFlashAttribute("message", "Product created sucessfully!");

		return "redirect:/products";
	}

	//@Transactional
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {

		productDao.update(product);

		redirectAttributes.addFlashAttribute("message", "User updated sucessfully!");

		return "redirect:/users";
	}

	//@Transactional
	@RequestMapping(value = { "/delete/{id}" }, method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") int id, Model model, RedirectAttributes redirectAttributes) {

		List<Product> product = productDao.read(id);

		if (product == null) {
			return "redirect:/products";
		}

		productDao.delete(id);

		redirectAttributes.addFlashAttribute("message", "Product deleted sucessfully!");

		return "redirect:/products";
	}
}