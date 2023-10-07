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

	


	
	
	
	
	
}
