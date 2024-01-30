package lv.venta.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.enums.Status;
import lv.venta.models.users.Academic_personel;
import lv.venta.models.users.Student;


@Setter
@Getter
@Entity
@Table(name="thesis_table")
@NoArgsConstructor
public class Thesis {

	@Column(name = "thesis_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long thesis_id;

	@Column(name = "TitleLv")
	@Size(min = 3, max = 25)
	private String titleLv;

	@Column(name = "TitleEn")
	@Size(min = 3, max = 25)
	private String titleEn;

	@Column(name = "aim")
	private String aim;

	@Column(name = "tasks")
	private String tasks;

	@Column(name = "SubmitDate" )
	private LocalDateTime submitDate;
	
	@Column(name = "StatussFromSupervisors")
	private boolean statusFromSupervisor;

	@Column(name = "Status")
	private Status status;
	
	@Column(name = "Accepted_Time")
	private LocalDateTime acceptedDate;
	
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "id_personel")
	private Academic_personel personel;
	
	@OneToMany(mappedBy = "thesis")
	private Collection<Comments> comments;
	
	@ManyToMany
	@JoinTable(name = "thesis_reviewers",
	joinColumns = @JoinColumn(name = "id_thesis"),
	inverseJoinColumns = @JoinColumn(name = "id_personel"))
	private Collection<Academic_personel> reviewers = new ArrayList<>();
	
	public void addReviewer(Academic_personel reviewer) {
		if(!reviewers.contains(reviewer)) {
			reviewers.add(reviewer);
		}
	}
	public void removeReviewer(Academic_personel reviewer) {
		if(reviewers.contains(reviewer)) {
			reviewers.remove(reviewer);
		}
	}
	
	
	public void addComment(Comments comment) {
		if(!comments.contains(comment)) {
			comments.add(comment);
		}
	}

	public Thesis(
			String titleLv,
			String titleEn,
			String aim,
			String tasks,
			Student student, Academic_personel personel) {
		super();
		this.titleLv = titleLv;
		this.titleEn = titleEn;
		this.aim = aim;
		this.tasks = tasks;
		this.student = student;
		this.personel = personel;
		this.submitDate = LocalDateTime.now();
		this.status = Status.Submitted;
	}

	

}
