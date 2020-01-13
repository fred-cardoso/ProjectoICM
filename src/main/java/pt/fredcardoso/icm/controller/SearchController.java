package pt.fredcardoso.icm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Product;

@Controller
@RequestMapping("/products")
public class SearchController {
	@Autowired
	private ProductDAO productDao;
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String search(Model model, HttpServletRequest searchRequest) {
		String searchWord = searchRequest.getParameter("sh");
		List<Product> products = productDao.read(-1);
		List<Product> listProductsToShow = new ArrayList<Product>();
		
		for(Product product : products){
			if(product.getName().toLowerCase().contains(searchWord.toLowerCase()))
				listProductsToShow.add(product);
		}
		
		model.addAttribute("products", listProductsToShow);
		

		return "products/search.html";
	}


}
