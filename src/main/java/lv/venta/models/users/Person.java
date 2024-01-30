package lv.venta.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.security.MyUser;

@Table(name = "person_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person {

	@Column(name = "id_person")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long personId;
	
	@Column(name = "name")
	@NotNull
	@Size(min = 3, max = 15)
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	private String personName;
	
	@Column(name = "surname")
	@NotNull
	@Size(min = 3, max = 15)
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	private String surname;
	
	
	@Column(name = "personalCode")
	@Pattern(regexp = "[0-9]{6}-[0-9]{5}", message = "Neatbilstošs personas kods")
	@NotNull
	private String personalCode;
	
	@OneToOne
	@JoinColumn(name = "id_user")
	private MyUser user;

	public Person(
			String personName,
			String surname,
			String personalCode,
			MyUser user) {
		super();
		this.personName = personName;
		this.surname = surname;
		this.personalCode = personalCode;
		this.user = user;
	}
	
	
}
