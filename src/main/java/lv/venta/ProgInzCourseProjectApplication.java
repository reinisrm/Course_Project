package lv.venta;

import java.time.LocalDateTime;

import lv.venta.models.Application;
import lv.venta.repos.IRepoApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import lv.venta.enums.Degree;
import lv.venta.models.Comments;
import lv.venta.models.Course;
import lv.venta.models.Thesis;
import lv.venta.models.users.Academic_personel;
import lv.venta.models.users.Student;
import lv.venta.models.users.User;
import lv.venta.repos.IRepoComments;
import lv.venta.repos.IRepoCourse;
import lv.venta.repos.IRepoThesis;
import lv.venta.repos.users.IRepoAcademicPersonel;
import lv.venta.repos.users.IRepoPerson;
import lv.venta.repos.users.IRepoStudent;
import lv.venta.repos.users.IRepoUser;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ProgInzCourseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgInzCourseProjectApplication.class, args);
	}
	
	//@Bean //Calls function when system runs
	public CommandLineRunner testModel(
			IRepoCourse courseRep,
			IRepoThesis thesisRep,
			IRepoAcademicPersonel academicPersonelRep,
			IRepoPerson personRep,
			IRepoStudent studentRep,
			IRepoUser userRep,
			IRepoComments commentsRep,
			IRepoApplication applicationRep) {
		
		
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {

				Application app1 = new Application();

				
			}
		};
	}
}


