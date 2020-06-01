package com.game.config;

import com.game.controller.AuthenticationHandlerController;
import com.game.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public AuthenticationHandlerController authenticationHandlerController;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/registration*", "/login*", "/registered*").not().hasAnyRole("USER", "ADMIN")
                .antMatchers("/", "/js/**", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .successHandler(authenticationHandlerController)
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/access_denied")
                .and()
                    .csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
