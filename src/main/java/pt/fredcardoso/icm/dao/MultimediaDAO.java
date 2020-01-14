package pt.fredcardoso.icm.dao;

import java.util.List;

import pt.fredcardoso.icm.model.Multimedia;

public interface MultimediaDAO {
	
    public Multimedia create(Multimedia multimedia);
    
    public List<Multimedia> read(String multimediaPath);
     
    public void update(Multimedia multimedia);
     
    public void delete(String multimediaPath);

}
