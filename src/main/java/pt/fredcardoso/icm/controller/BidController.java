package pt.fredcardoso.icm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.BidForm;
import pt.fredcardoso.icm.services.BidService;

@Controller
@RequestMapping("/bid")
public class BidController {
	
	@Autowired
	private BidService bidService;
	
	@RequestMapping(value = { "/create" }, method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("bid") BidForm bid, BindingResult result, HttpServletRequest request) {
		
		Bid bidCreated = null;
		User user = (User) request.getSession().getAttribute("user");
		
		if (!result.hasErrors()) {
			bidCreated = bidService.create(bid, user);
		}
		
		if(bidCreated == null) {
			result.reject("Unknown error ocurred.");
			return "redirect:/500";
		}
		
		return "redirect:/products/" + bid.getProductId();
	}
}
