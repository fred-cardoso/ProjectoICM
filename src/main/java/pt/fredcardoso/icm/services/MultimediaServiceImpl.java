package pt.fredcardoso.icm.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import pt.fredcardoso.icm.dao.MultimediaDAO;
import pt.fredcardoso.icm.model.Multimedia;

public class MultimediaServiceImpl implements MultimediaService {
	
	@Autowired
	private MultimediaDAO multimediaDao;
	

	
	private final String PATH = "/resources/uploads/";

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
	
	@SuppressWarnings("static-access")
	public List<Multimedia> create(int productId, List<File> fileList) {
		List<Multimedia> returnList = new ArrayList<Multimedia>();
		
		for(File saveMedia : fileList) {
			Multimedia media = new Multimedia();
			BCrypt x = new BCrypt();
			String salt = BCrypt.gensalt(11);
			media.setPath(PATH + x.hashpw(saveMedia.getName(), salt));
			media.setProductid(productId);
			returnList.add(multimediaDao.create(media));
		}
		return returnList;
	}

}
