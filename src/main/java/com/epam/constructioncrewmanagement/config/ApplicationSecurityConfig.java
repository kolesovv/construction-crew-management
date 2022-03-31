package com.epam.constructioncrewmanagement.config;

import static com.epam.constructioncrewmanagement.entity.Role.ADMIN;
import static com.epam.constructioncrewmanagement.entity.Role.USER;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/login", "/registration", "/css/*", "/img/*").permitAll()
        .antMatchers(HttpMethod.POST).permitAll()
        .antMatchers("/projects/add", "/employees/add").hasRole(ADMIN.name())
        .antMatchers("/projects/*","/employee").hasAnyRole(ADMIN.name(), USER.name())
        .antMatchers("/projects/*/**","/employees/*/**").hasRole(ADMIN.name())
        .anyRequest()
        .authenticated()
        .and()
        .authorizeRequests()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/projects", true);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder(12);
  }
}
