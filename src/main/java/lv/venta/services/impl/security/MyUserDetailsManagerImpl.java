package lv.venta.services.impl.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import lv.venta.utils.MyUserDetails;
import lv.venta.models.security.MyUser;
import lv.venta.repo.security.IMyUserRepo;
@Service
public class MyUserDetailsManagerImpl implements UserDetailsManager {

	@Autowired
	private IMyUserRepo userRepo;
	
	
	public List<MyUser> allUsers() {
		
		return (List<MyUser>) userRepo.findAll();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser user = userRepo.findByUsername(username);
		if(user != null) {
			MyUserDetails details = new MyUserDetails(user);
			return details;
		} else {
			throw new UsernameNotFoundException(username + "nav atrasts datubazee");
		}
		
	}

	@Override
	public void createUser(UserDetails user) {
		MyUserDetails myDetails = (MyUserDetails) user;
		MyUser myUser = myDetails.getUser();
		
		userRepo.save(myUser);
		
	}

	@Override
	public void updateUser(UserDetails user) {
		MyUserDetails myDetails = (MyUserDetails) user;
		MyUser myUser = myDetails.getUser();
		
		userRepo.save(myUser);
		
	}

	@Override
	public void deleteUser(String username) {
		MyUser user = userRepo.findByUsername(username);
		if(user != null) { //eksistee tads lietotajs datubazee
			userRepo.delete(user);
			
		}
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {

		
	}

	@Override
	public boolean userExists(String username) {
		MyUser user = userRepo.findByUsername(username);
		
		if(user != null) {
			return true;
		} else {
			return false;
		}
		

	}

}
