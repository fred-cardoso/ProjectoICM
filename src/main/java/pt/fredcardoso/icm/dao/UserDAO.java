package pt.fredcardoso.icm.dao;

import java.util.List;

import pt.fredcardoso.icm.model.User;

public interface UserDAO {
	
    public User create(User user);
    
    public List<User> read(int userId);
    
    public List<User> read(String email);
     
    public void update(User user);
     
    public void delete(int userId);

}
