/*
package lv.venta.confs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lv.venta.services.impl.security.MyUserDetailsManagerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	//TODO Nomainit no h2 uz SQL.
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**")); 
	}
	@Bean
	public MyUserDetailsManagerImpl userDetailsManeger() {
		MyUserDetailsManagerImpl manager = new MyUserDetailsManagerImpl();
		return manager;
	}

	@Bean
	PasswordEncoder passwordEncoderSimple2() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		
		
		authenticationManagerBuilder.
		userDetailsService(userDetailsManeger()).passwordEncoder(passwordEncoderSimple2());
		return authenticationManagerBuilder.build();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()

			.requestMatchers("/personel/showAll").permitAll()
			.requestMatchers("/personel/showOne/**").permitAll()
			.requestMatchers("/personel/delete/**").permitAll()
			.requestMatchers("/personel/add").permitAll()
			.requestMatchers("/personel/update/**").permitAll()
			.requestMatchers("/comments/showAll").permitAll()
			.requestMatchers("/comments/showOne/**").permitAll()
			.requestMatchers("/comments/add").permitAll()
			//.requestMatchers("/comments/delete").permitAll()
			.requestMatchers("/courses/showAll").permitAll()
			.requestMatchers("/courses/showOne/**").permitAll()
*/ //		.requestMatchers("/courses/add").permitAll()
//			.requestMatchers("/courses/addDebt/**/**").permitAll()
//			.requestMatchers("/courses/removeDebt/**/**").permitAll()
//			.requestMatchers("/login").permitAll()
//			.requestMatchers("/register").permitAll()
//			.requestMatchers("/student/showAll").permitAll()
//			.requestMatchers("/student/show/**").permitAll()
//			.requestMatchers("/student/remove/**").permitAll()
//			.requestMatchers("/student/insertNew").permitAll()
//			.requestMatchers("/update/**").permitAll()
//			.requestMatchers("/thesis/showAll").permitAll()
//			.requestMatchers("/thesis/show/**").permitAll()
//			.requestMatchers("/thesis/remove/**").permitAll()
//			.requestMatchers("/thesis/insertNew").permitAll()
//			.requestMatchers("/thesis/update/**").permitAll()
//			.and()
//			.formLogin().permitAll()
//			.and()
//			.logout().permitAll();
/*
		return http.build();
	}

	


	
	
	
	
	
}
*/