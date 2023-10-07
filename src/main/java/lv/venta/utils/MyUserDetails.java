package lv.venta.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lv.venta.models.security.MyAuthority;
import lv.venta.models.security.MyUser;

public class MyUserDetails implements UserDetails {
	
	private MyUser user;
	
	public MyUserDetails(MyUser user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Iterator<MyAuthority> iter = user.getAuthorities().iterator();
		
		while(iter.hasNext()) {
			SimpleGrantedAuthority temp = new SimpleGrantedAuthority(iter.next().getTitle());
			authorities.add(temp);
		}
		
		return authorities;
	}
	
	
	public void setUser(MyUser user) {
		this.user = user;
	}

	public MyUser getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

