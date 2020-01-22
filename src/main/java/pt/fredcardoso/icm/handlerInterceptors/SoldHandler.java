package pt.fredcardoso.icm.handlerInterceptors;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.services.SoldService;

@Component
public class SoldHandler implements HandlerInterceptor {
	
	@Autowired
	private ProductDAO productDao;
	
	@Autowired
	private SoldService soldService;
	
	public boolean preHandle(
	  HttpServletRequest request,
	  HttpServletResponse response, 
	  Object handler) throws Exception {
	     
		List<Product> products = productDao.read(-1);
		
		for(Product p : products) {
			if(!p.isSold() && p.getAwardMode().contentEquals(Product.AUCTION_PERIOD)) {
				Date date = new Date();
				
				System.out.println("ACTUAL: "  + date);
				System.out.println("DO PRODUTO: " + p.getAuctionPeriod());
				if(date.after(p.getAuctionPeriod())) {
					System.out.println("TESTE");
					soldService.sellProductAfterAuctionPeriod(p.getId());
				}
			}
		}
		
		return true;
	}
}
