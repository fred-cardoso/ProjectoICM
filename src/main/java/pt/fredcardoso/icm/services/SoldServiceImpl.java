package pt.fredcardoso.icm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.SoldDAO;
import pt.fredcardoso.icm.model.Sold;

public class SoldServiceImpl implements SoldService {
	
	@Autowired
	private SoldDAO soldDao;

	public boolean checkSoldFromProductId(long productId) {
		List<Sold> solds = soldDao.read(-1);
		
		if(solds == null) {
			return false;
		}
		
		for (Sold sold : solds) {
			if(sold.getProductId() == productId) {
				return true;
			}
		}
		
		return false;
	}
}
