package lv.venta.services.users.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.User;
import lv.venta.repos.users.IRepoPerson;
import lv.venta.repos.users.IRepoUser;
import lv.venta.services.users.IUserCRUDService;

@Service
public class UserCRUDService implements IUserCRUDService{
	
	@Autowired
	private IRepoUser userRepo;
	
	@Override
	public User findByEmail(String email) {
		
	    return userRepo.findByEmail(email);
	    
	}
	


	@Override
	public void deleteByID(int ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> allUsers() {
		
		return (List<User>) userRepo.findAll();
	}

	@Override
	public void registerUser(User user) {
	    try {
	        for(User temp: allUsers()) {
	        	if(temp.getEmail().equals(user.getEmail())) {
	        		return;
	        	}
	        }
	        
	        
	        userRepo.save(user);
	        
	    } catch (Exception e) {
	        // Handle the exception if necessary
	    }
	}

	
	
	
	

}
