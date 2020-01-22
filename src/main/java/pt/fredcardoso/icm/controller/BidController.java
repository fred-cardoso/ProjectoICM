package pt.fredcardoso.icm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.BidForm;
import pt.fredcardoso.icm.services.BidService;
import pt.fredcardoso.icm.services.ProductService;

@Controller
@RequestMapping("/bid")
public class BidController {

	@Autowired
	private ProductService productService;

	@Autowired
	private BidService bidService;

	@RequestMapping(value = { "/create" }, method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("bid") BidForm bid, BindingResult result, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		Bid bidCreated = null;
		User user = (User) request.getSession().getAttribute("user");

		if (!result.hasErrors()) {

			if (bid.getValue() < productService.getLatestBidValue(bid.getProductId())) {
				result.rejectValue("value", "Valor inferior ao actual. Aumente o valor da sua licitação.");
				redirectAttributes.addFlashAttribute("fields", result.getAllErrors());
				return "redirect:/products/" + bid.getProductId();
			}

			bidCreated = bidService.create(bid, user);
		}

		if (bidCreated == null) {
			result.reject("Unknown error ocurred.");
			return "redirect:/500";
		}

		return "redirect:/products/" + bid.getProductId();
	}
}
