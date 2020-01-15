package pt.fredcardoso.icm.services;

import java.io.File;
import java.util.List;

import pt.fredcardoso.icm.model.Multimedia;

public interface MultimediaService {

	public List<Multimedia> getMultimediaOfProduct(int productId);
	public List<Multimedia> create(int productId, List<File> Filelist);
	
}
