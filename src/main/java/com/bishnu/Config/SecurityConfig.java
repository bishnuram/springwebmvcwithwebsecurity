package com.bishnu.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
@Component
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService getDetailsService()
	{ 
		return new CustomUserDetailsService(); 
		}
	
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http		
			.authorizeHttpRequests(authorize->authorize
		                .requestMatchers("/", "/register").permitAll()
		                .anyRequest().authenticated())
		
			.formLogin(login->login
				.loginPage("/loginPage")
				.loginProcessingUrl("/login")
				.permitAll()
				.defaultSuccessUrl("/profile")
				.failureUrl("/loginPage"))
//			.logout(logout -> logout
//	                .logoutUrl("/logout")
//	                .logoutSuccessUrl("/loginPage?logout")
//	                .invalidateHttpSession(true)
//	                .deleteCookies("JSESSIONID")
//	                .permitAll())
			;
				return http.build();
		
	}
	

}
