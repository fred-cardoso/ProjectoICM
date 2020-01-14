package pt.fredcardoso.icm.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.fredcardoso.icm.dao.MultimediaDAO;
import pt.fredcardoso.icm.model.Multimedia;

public class MultimediaServiceImpl implements MultimediaService {
	
	@Autowired
	private MultimediaDAO multimediaDao;

	public List<Multimedia> getMultimediaOfProduct(int productId) {
		List<Multimedia> resultados = new ArrayList<Multimedia>();
		
		List<Multimedia> multimedias = multimediaDao.read(null);
		
		for(Multimedia multimedia : multimedias) {
			if(multimedia.getProductid() == productId) {
				resultados.add(multimedia);
			}
		}
		
		return resultados;
	}
}
