package pt.fredcardoso.icm.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.form.Search;

@Controller
@RequestMapping("/products")
public class SearchController {
	@Autowired
	private ProductDAO productDao;
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String search(Model model) {
		String searchWord = "";
		List<Product> products = productDao.read(-1);
		Search search = new Search();
		search.setSearch(searchWord);
		model.addAttribute("products", products);
		model.addAttribute("search", search);
		

		return "products/search.html";
	}


}
