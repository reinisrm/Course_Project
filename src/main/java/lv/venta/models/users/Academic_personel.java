package lv.venta.models.users;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.enums.Degree;
import lv.venta.models.Application;
import lv.venta.models.Comments;
import lv.venta.models.Thesis;
import lv.venta.models.security.MyUser;

@Setter
@Getter
@Entity
@Table(name="personel_table")
@NoArgsConstructor
@AttributeOverride(name = "id_person", column = @Column(name = "id_personel"))
public class Academic_personel extends Person{
	
	
	@Column(name="degree")
	private Degree degree;
	
	@OneToMany(mappedBy = "personel")
	private Collection<Thesis> thesis;
	
	@ManyToMany(mappedBy = "reviewers")
	private Collection<Thesis> ThesisForReview = new ArrayList<>();

	@ManyToMany(mappedBy = "recipients")
	private Collection<Application> applications;

	@OneToMany(mappedBy = "personel")
	private Collection<Comments> comments;
	
	public Academic_personel(String name,
		String surname,
		String personalCode,
		MyUser user,
		Degree degree) {
	super(name, surname, personalCode, user);
	this.degree = degree;
	}
	
	public void addThesisForReview(Thesis thesis) {
		if(!ThesisForReview.contains(thesis)) {
			ThesisForReview.add(thesis);
		}
	}
	
	public void removeThesisForReview(Thesis thesis) {
		if(ThesisForReview.contains(thesis)) {
			ThesisForReview.remove(thesis);
		}
	}


}
	


