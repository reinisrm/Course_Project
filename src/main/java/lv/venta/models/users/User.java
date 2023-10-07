package lv.venta.models.users;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.*;
import lombok.*;

@Table(name = "user_table")
@Entity
@Data
@NoArgsConstructor
public class User {
	
	

	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "id_user")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;
	
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	@Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{3,}", message = "Pirmajam burtam jābūt mazajam")
	private String email;
	
	@OneToOne(mappedBy = "user")
	@ToString.Exclude
	private Person person;
	
	public User(
			@Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{3,}", message = "Pirmajam burtam jābūt mazajam") String email, String password) {
		super();
		this.email = email;
		setPassword(password);
	}
	

	
	public void setPassword(String password) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		this.password = passwordEncoder.encode(password);
		
	}



	
	
	
}
