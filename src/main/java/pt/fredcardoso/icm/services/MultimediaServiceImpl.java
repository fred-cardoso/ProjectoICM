package pt.fredcardoso.icm.services;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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

		for (Multimedia multimedia : multimedias) {
			if (multimedia.getProductid() == productId) {
				resultados.add(multimedia);
			}
		}

		return resultados;
	}

	@SuppressWarnings("static-access")
	public List<Multimedia> create(int productId, List<File> fileList) {
		List<Multimedia> returnList = new ArrayList<Multimedia>();

		for (File saveMedia : fileList) {
			Multimedia media = new Multimedia();
			BCrypt x = new BCrypt();
			String salt = BCrypt.gensalt(11);
			String filename = x.hashpw(saveMedia.getName(), salt).replaceAll("/", "l")
					.replace(".", "a");

			media.setPath(PATH + productId + "/" + filename + getExtension(saveMedia.getName()));
			media.setProductid(productId);
			returnList.add(multimediaDao.create(media));

			saveToFolder(saveMedia, productId, filename);
		}
		return returnList;
	}

	private String getExtension(String name) {
		String extension = "";
		int i = name.lastIndexOf('.');
		if (i > 0) {
			extension = name.substring(i);
		}
		return extension;
	}

	private void saveToFolder(File saveMedia, int productId, String filename) {
		String directory = System.getProperty("user.dir")
				+ "\\ICM-ProjetoFinal\\ProjectoICM\\src\\main\\webapp\\WEB-INF\\resources\\uploads\\"
				+ String.valueOf(productId);
		System.out.println(directory);
		File fileDirectory = new File(directory);
		System.out.println("YOYO : " + directory + "\\" + filename + getExtension(saveMedia.getName()));
		File newFile = new File(directory + "\\" + filename + getExtension(saveMedia.getName()));
		while(fileDirectory.mkdir()) {

		}
		try {
			newFile.createNewFile();

			
			if(getExtension(saveMedia.getName()).equalsIgnoreCase(".png")) {
				BufferedImage image = ImageIO.read(saveMedia);
				ImageIO.write(image, "png", newFile);
			};
			if(getExtension(saveMedia.getName()).equalsIgnoreCase(".mp3")) {
				copyAudio(saveMedia,newFile);
			}else {

				copyFile(saveMedia, newFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		/*
		 * try { //create FileInputStream object
		 * 
		 * 
		 * }
		 */

	}

	private void copyAudio(File saveMedia, File newFile) {
		File f = saveMedia;
		InputStream is;
		OutputStream outstream;
		try {
			is = new FileInputStream(f);
			outstream = new FileOutputStream(newFile);
			byte[] buffer = new byte[4096];
			int len;
			while ((len = is.read(buffer)) > 0) {
				System.out.println(buffer);
			    outstream.write(buffer);
			}
			outstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void copyFile(File saveMedia, File newFile) {
		final int BUFFERSIZE = 4 * 1024;
		try (FileInputStream fin = new FileInputStream(saveMedia);
				FileOutputStream fout = new FileOutputStream(newFile);) {

			byte[] buffer = new byte[BUFFERSIZE];

			while (fin.available() != 0) {
				fout.write(buffer);
			}

		} catch (Exception e) {
			System.out.println("Something went wrong! Reason: " + e.getMessage());
		}

	}

}
