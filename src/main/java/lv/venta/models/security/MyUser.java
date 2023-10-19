package lv.venta.models.security;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.venta.models.users.Person;

@Entity
@Table(name = "user_table")
@Data
@NoArgsConstructor
public class MyUser {

	@Column(name = "id_user")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	@Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{3,}", message = "Pirmajam burtam jābūt mazajam")
	private String email;
	
	@NotNull
	@Column(name = "Username")
	private String username;
	
	
	@OneToOne(mappedBy = "user")
	@ToString.Exclude
	private Person person;
	
	
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Collection<MyAuthority> authorities = new ArrayList<>();
	
	
		
	public Collection<MyAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<MyAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public MyUser(
			@Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{3,}", message = "Pirmajam burtam jābūt mazajam") String email, String password) {
		super();
		this.email = email;
		setPassword(password);
		username = email.toLowerCase();
	}
	
	public void addAuthority(MyAuthority authority) {
		if(!authorities.contains(authority)) {
			authorities.add(authority);
		}
	}
	public void removeAuthority(MyAuthority authority) {
		if(authorities.contains(authority)) {
			authorities.remove(authority);
		}
	}
	
	
	
}
