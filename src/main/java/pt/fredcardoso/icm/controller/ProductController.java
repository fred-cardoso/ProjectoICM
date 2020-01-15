
package pt.fredcardoso.icm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.BidForm;
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

		return "products/index.html";
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public String product(@PathVariable(value = "id") int id, Model model) {

		List<Product> products = productDao.read(id);
		Product product = null;
		
		if(products == null) {
			return "redirect:/404";
		}
		
		product = products.get(0);

		model.addAttribute("product", product);
		model.addAttribute("bid", new BidForm());

		return "products/details.html";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("product", new ProductForm());

		return "products/create.html";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("product") ProductForm product, BindingResult result, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		Product productCreated = null;

		if (!result.hasErrors()) {
			productCreated = productService.create(product, (User) request.getSession().getAttribute("user"));
		}

		if (productCreated == null) {
			result.rejectValue("error", "Unknown error ocurred.");
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
	
	@RequestMapping(value = { "/realtime" }, method = RequestMethod.GET)
	public String realTime(Model model, RedirectAttributes redirectAttributes) {
		
		List<Product> products = productDao.read(-1);

		model.addAttribute("products", products);

		return "products/realtime.html";
	}
	
	@RequestMapping(value = { "/realtime/{id}" }, method = RequestMethod.GET)
	public String realTimeShowGraph(@PathVariable(value = "id") int id, Model model, RedirectAttributes redirectAttributes) {
		
		model.addAttribute("product", id);

		return "products/graph.html";
	}
}