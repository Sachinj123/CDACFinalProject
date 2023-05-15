package com.raktkosh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.raktkosh.security.JWTAuthEntrypoint;
import com.raktkosh.security.JWTAuthTokenFilter;
import com.raktkosh.services.UserDetailsServiceImpl;

/**
 * The @Configuration indicates that this is a configuration class.
 * The @EnableWebSecurity enables the web securities defined by WebSecurityConfigurerAdapter automatically.
 * 
 * WebSecurityConfigurerAdapter is a convenience class that allows customization to both WebSecurity and HttpSecurity.
 * To override web securities defined by WebSecurityConfigurerAdapter in our Java configuration class,
 * we need to extend this class and override its methods.
 * 
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  
  @Autowired
  private JWTAuthEntrypoint unauthorizedHandler;
  
  @Bean
  public JWTAuthTokenFilter authenticationJwtTokenFilter() {
    return new JWTAuthTokenFilter();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  //
	  http.cors().and().csrf().disable().exceptionHandling()
	  //
      .authenticationEntryPoint(unauthorizedHandler)
      .and().sessionManagement()
      //
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().authorizeRequests()
      //
      .antMatchers("/account/**").permitAll()
      .anyRequest().authenticated();
    
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
