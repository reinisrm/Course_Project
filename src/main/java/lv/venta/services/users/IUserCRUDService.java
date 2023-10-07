package lv.venta.services.users;

import java.util.List;

import lv.venta.models.users.User;

public interface IUserCRUDService {
	
	User findByEmail(String email) throws Exception;
	
	void deleteByID(int ID);
	
	List<User> allUsers();
	
	void registerUser(User user);
	
	

}
