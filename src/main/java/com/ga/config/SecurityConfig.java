package com.ga.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ga.service.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.ga")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean("encoder")
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//	    User.UserBuilder users = User.withDefaultPasswordEncoder();
//
//	    auth.inMemoryAuthentication().withUser(users.username("test").password("test").roles("ADMIN"));
//	    auth.inMemoryAuthentication().withUser(users.username("dba").password("dba").roles("DBA"));
//	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/signup/**", "/user/login/**").permitAll()
    	        .antMatchers("/user/**", "/role/**", "/song/**").authenticated()
    	        // Getting 403 Forbidden despite user having ROLE_ADMIN
//    	        .antMatchers("/user/{id}").hasRole("ADMIN")
//    	        .antMatchers("/user/delete/{id}").hasRole("ADMIN")
//    	        .antMatchers("/role/**").hasRole("ADMIN")
//    	        .antMatchers("/song/**").hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}