
package lv.venta.confs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import lv.venta.services.impl.security.MyUserDetailsManagerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	//@Bean
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
		http.authorizeHttpRequests(auth -> auth
						.requestMatchers("/home").permitAll()
						.requestMatchers("/personel/showAll").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/personel/showOne/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/personel/delete/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/personel/add").hasAnyAuthority("ADMIN")
						.requestMatchers("/personel/update/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/comments/showAll").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/comments/showOne/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/comments/add").hasAnyAuthority("ADMIN")
						.requestMatchers("/comments/delete").hasAnyAuthority("ADMIN")
						.requestMatchers("/courses/showAll").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/courses/showOne/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/courses/add").hasAnyAuthority("ADMIN")
						.requestMatchers("/courses/addDebt/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/courses/removeDebt/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/courses/delete/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/student/showAll").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/student/show/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/student/remove/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/student/insertNew").hasAnyAuthority("ADMIN")
						.requestMatchers("/student/update/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/thesis/showAll").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/thesis/show/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/thesis/remove/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/thesis/insertNew").hasAnyAuthority("ADMIN")
						.requestMatchers("/thesis/update/**").hasAnyAuthority("ADMIN"))
						.formLogin(login -> login
								.defaultSuccessUrl("/home")  // Redirect after successful login
								.permitAll())
						.logout(logout -> logout
								.permitAll());




		 
		

		return http.build();
	}

	


	
	
	
	
	
}
