package lv.venta.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.AttributeOverride;
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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.enums.Degree;
import lv.venta.enums.Status;
import lv.venta.models.users.Academic_personel;
import lv.venta.models.users.Student;

/*
 * 
 * 
 * 
 * 
 * 
 * 
 */


@Setter
@Getter
@Entity
@Table(name="thesis_table")
@NoArgsConstructor
public class Thesis {
	
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "thesis_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long thesis_id;
	
	
	//TODO pieviento validacijas	
	@Column(name = "TitleLv")
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Size(min = 5, max = 25)
	private String titleLv;
	
	//TODO pieviento validacijas
	@Column(name = "TitleEn")
	@Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Size(min = 5, max = 25)
	private String titleEn;
	
	//TODO pieviento validacijas	
	@Column(name = "aim")
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	private String aim;
		
	//TODO pieviento validacijas
	@Column(name = "tasks")
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	private String tasks;
	
	//TODO pie jauna objekta izveides jauzliek LocalDateTime.now()
	@Column(name = "SubmitDate" )
	private LocalDateTime submitDate;
	
	@Column(name = "StatussFromSupervisors")
	private boolean statusFromSupervisor;
	
	//TODO uzlikt Submitted as default
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
	
	public void addComment(Comments comment) {
		if(!comments.contains(comment)) {
			comments.add(comment);
		}
	}

	public Thesis(
			@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 5, max = 25) String titleLv,
			@Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 5, max = 25) String titleEn,
			@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") String aim,
			@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") String tasks,
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
