package com.donte.aluraflix.config.basicauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.donte.aluraflix.config.SimpleAccessDeniedHandler;
import com.donte.aluraflix.config.SimpleAuthEntryPoint;

@Profile("basic-auth")
@Configuration
public class WebBasicConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{

	@Autowired
	private UserDetailsService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
		.and()
		.authorizeRequests()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers("/videos/free").permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling()
			.accessDeniedHandler(new SimpleAccessDeniedHandler())
			.authenticationEntryPoint(new SimpleAuthEntryPoint())
		.and()
		.csrf().disable();

		http.headers().frameOptions().disable();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("*").allowedOrigins("http://localhost:4200").allowCredentials(true);
	}

}
