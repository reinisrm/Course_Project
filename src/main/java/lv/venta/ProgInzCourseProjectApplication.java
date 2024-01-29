package lv.venta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import lv.venta.models.Application;
import lv.venta.repos.IRepoApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


import lv.venta.enums.Degree;
import lv.venta.models.Comments;
import lv.venta.models.Course;
import lv.venta.models.Thesis;
import lv.venta.models.security.MyAuthority;
import lv.venta.models.security.MyUser;
import lv.venta.models.users.Academic_personel;
import lv.venta.models.users.Student;
import lv.venta.repo.security.IMyAuthorityRepo;
import lv.venta.repo.security.IMyUserRepo;
import lv.venta.repos.IRepoComments;
import lv.venta.repos.IRepoCourse;
import lv.venta.repos.IRepoThesis;
import lv.venta.repos.users.IRepoAcademicPersonel;
import lv.venta.repos.users.IRepoPerson;
import lv.venta.repos.users.IRepoStudent;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ProgInzCourseProjectApplication implements WebMvcConfigurer {

	private final LocaleChangeInterceptor localeChangeInterceptor;

	public ProgInzCourseProjectApplication(LocaleChangeInterceptor localeChangeInterceptor) {
		this.localeChangeInterceptor = localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor(localeChangeInterceptor);
	}


	public static void main(String[] args) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("lang/messages");
		messageSource.setDefaultEncoding("UTF-8");
		SpringApplication.run(ProgInzCourseProjectApplication.class, args);

	}

	@Bean
	public PasswordEncoder passwordEncoderSimple() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	//@Bean //Calls function when system runs
	public CommandLineRunner testModel(
			IRepoCourse courseRep,
			IRepoThesis thesisRep,
			IRepoAcademicPersonel academicPersonelRep,
			IRepoPerson personRep,
			IRepoStudent studentRep,
			IRepoComments commentsRep,
			IRepoApplication applicationRep,
			IMyAuthorityRepo authorityRep,
			IMyUserRepo myUserRep) {

		{

			return new CommandLineRunner() {

				@Override
				public void run(String... args) throws Exception {



					MyUser user1 = new MyUser("karinaskirmante@gmail.com", passwordEncoderSimple().encode("123"));
					myUserRep.save(user1);

					MyUser user2 = new MyUser("karlisimmers@gmail.com", passwordEncoderSimple().encode("321"));
					myUserRep.save(user2);

					MyUser user3 = new MyUser("arvidsivbuls@gmail.com", passwordEncoderSimple().encode("333"));
					myUserRep.save(user3);

					MyUser user4 = new MyUser("zigiscelotajs@gmail.com", passwordEncoderSimple().encode("111"));
					myUserRep.save(user4);

					MyAuthority auth1 = new MyAuthority("ADMIN");
					MyAuthority auth2 = new MyAuthority("USER");


					auth1.addUser(user1); // Karina ka ADMIN
					auth1.addUser(user2); // Karlis ka ADMIN
					auth2.addUser(user1); // Karina ka USER
					auth2.addUser(user2); // Karlis ka USER
					auth2.addUser(user3); //Arvids ka user
					auth2.addUser(user4); //Zigis ka user
					authorityRep.save(auth1);
					authorityRep.save(auth2);

					user1.addAuthority(auth1);
					user1.addAuthority(auth2);
					user2.addAuthority(auth1);
					user2.addAuthority(auth2);
					user3.addAuthority(auth2);
					user4.addAuthority(auth2);
					myUserRep.save(user1);
					myUserRep.save(user2);
					myUserRep.save(user3);
					myUserRep.save(user4);


					//Courses
					Course c1 = new Course("Java_", 4);
					Course c2 = new Course("DataStr", 2);
					courseRep.save(c1);
					courseRep.save(c2);


					//Academic personel / Students
					Academic_personel ac1 = new Academic_personel("Karina", "Skirmante", "123455-24234", user1, Degree.master);
					Academic_personel ac2 = new Academic_personel("Karlis", "Immers", "123455-00001", user2, Degree.master);
					academicPersonelRep.save(ac1);
					academicPersonelRep.save(ac2);
					Student student1 = new Student("Arvids", "Ivbuls", "203421-92342", user3, "123312321");
					Student student2 = new Student("Zigis", "Celotajs", "422452-12343", user4, "00142422");
					student2.addDebtCourse(c1);
					studentRep.save(student1);
					studentRep.save(student2);

					c1.addStudent(student2);
					//=======SAVE===========
					courseRep.save(c1);
					//courseRep.save(c2);
					//======================


					//Thesis
					Thesis th1 = new Thesis("Sistemas izstrade", "System development", "Development", "Abc", student1, ac1);
					Thesis th2 = new Thesis("Kripto", "Crypto", "Blockchain", "Abc", student2, ac2);
					th1.addReviewer(ac2);
					th2.addReviewer(ac1);
					thesisRep.save(th1);
					thesisRep.save(th2);
					ac1.addThesisForReview(th2);
					ac2.addThesisForReview(th1);
					academicPersonelRep.save(ac1);
					academicPersonelRep.save(ac2);


					//Comments
					Comments comm1 = new Comments("Lielisks darbs!", LocalDateTime.now(), ac1, th1);
					commentsRep.save(comm1);

					Collection<Academic_personel> recipients = new ArrayList<>();
					Academic_personel personel1 = new Academic_personel("Karina", "Skirmante", "111101-11223", null, Degree.master);


					studentRep.save(student1);
					academicPersonelRep.save(personel1);


					recipients.add(personel1);


					Application app1 = new Application("Iesniegums", "Sveiki, mans iesniegums ir loti radoss, es velejos uzzinat par iespejam...", student1, recipients);

					applicationRep.save(app1);


				}
			};
		}
	}
}


