package com.fix.demo.config.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoderImp passwordEncoderImp;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    /**
     * this method configures which endpoints will be accessible
     * out of the box, and which ones will need login
     *
     * @param http
     * @throws Exception
     */
    //TODO: figure out about the login and logout issues
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/sample").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/sample")
                .permitAll();
    }


    /**
     * This method validates the user login.
     *
     * @param auth Authenticator
     * @throws Exception if the authentication fails
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp).passwordEncoder(passwordEncoderImp);
    }
}