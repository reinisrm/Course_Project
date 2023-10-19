package lv.venta.models.users;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.Application;
import lv.venta.models.Course;
import lv.venta.models.Thesis;
import lv.venta.models.security.MyUser;

@Table(name = "student_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "id_person", column = @Column( name = "studentId"))
public class Student extends Person{
	
	

	@Column(name = "matriculaNo")
	private String matriculaNo;
	
	@Column(name = "isDebt")
	private boolean Debt;

	private boolean isDebt() {
		return this.Debt;
	}

	@ManyToMany
	@JoinTable(name = "student_debt_table",
	joinColumns = @JoinColumn(name = "course_id"),
	inverseJoinColumns = @JoinColumn(name = "person_id"))
	private Collection<Course> debtCourses = new ArrayList<>();
	
	@OneToMany(mappedBy = "student")
	private Collection<Thesis> thesis;

	@OneToMany(mappedBy = "author")
	private Collection<Application> applications;


	
	public Student(
<<<<<<< HEAD
			String personName,
		String surname,
		String personalCode,
		User user,
			String matriculaNo) {
=======

		@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") String personName,
		@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") String surname,
		@Pattern(regexp = "[0-9]{6}-[0-9]{5}\\ ]+", message = "Neatbilstošs personas kods") @NotNull String personalCode,
		MyUser user, @NotNull @Pattern(regexp = "[0-9]{8,20}") String matriculaNo) {
>>>>>>> f623841a0325b19c9f18e2e0d7e592854abe34c8
	super(personName, surname, personalCode, user);
	this.matriculaNo = matriculaNo;
	}
	
	
	
	public void addDebtCourse(Course course) {
		if(!debtCourses.contains(course)) {
			debtCourses.add(course);
		}
	}


}


	
	

