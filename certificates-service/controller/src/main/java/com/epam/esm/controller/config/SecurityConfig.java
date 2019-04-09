package com.epam.esm.controller.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public static PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Configuration
    public static class InnerSecurityConfig extends WebSecurityConfigurerAdapter{
        private UserDetailsService userDetailsService;

        public InnerSecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService){
            this.userDetailsService = userDetailsService;
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(encoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/certificates").permitAll()
                    .antMatchers(HttpMethod.POST, "/user/login", "/user/signup").permitAll()
                    .antMatchers(HttpMethod.GET, "/tags").hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/order").hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/certificates", "/tags").hasRole("ADMIN")
                    .anyRequest().authenticated();
        }
    }
}