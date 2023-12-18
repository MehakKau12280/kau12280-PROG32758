package ca.sheridancollege.kau12280.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	 
	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
	}

 @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		
		//these two lines are testing purpose only
		http.csrf().disable(); 
		http.headers().frameOptions().disable();

		http.authorizeRequests()
		 
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/",false)
		 
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()
		.and()
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler);		
		
		return http.build();
	}
}
