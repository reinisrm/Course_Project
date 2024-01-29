package lv.venta.models;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.models.users.Student;

@Table(name = "Table_Course")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Course {

	@Column(name = "course_title")
	@Size(min = 3, max = 25)
	private String title;

	@Column(name = "course_creditpoints")
	@Min(value = 1)
	@Max(value = 10)
	private int creditPoints;

	@Column(name = "course_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long courseId;


	@ManyToMany(mappedBy = "debtCourses")
	private Collection<Student> studentsWithDebt = new ArrayList<>();

	public Course(
		String title,
		int creditPoints) {
	this.title = title;
	this.creditPoints = creditPoints;
	}
	
	
	public void addStudent(Student student) {
		if(!studentsWithDebt.contains(student)) {
			studentsWithDebt.add(student);
		}
	}

}


