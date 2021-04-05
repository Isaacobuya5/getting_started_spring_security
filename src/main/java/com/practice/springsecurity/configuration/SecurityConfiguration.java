package com.practice.springsecurity.configuration;

import com.practice.springsecurity.service.AuthenticationSuccessHandlerImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // override this to build our own http authentication and authorization strategies
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/login").permitAll() // configure login endpoint to permit all requests
                .mvcMatchers("/support/admin").hasRole("ADMIN") // to access this, user must have role "ADMIN"
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic(); // support only basic authentication
                .formLogin() //support form authentication
                .loginPage("/login") // provide custom login page // you can also add permitAll here
                .successHandler(new AuthenticationSuccessHandlerImpl());
    }

    // by default, spring blocks everything including javascript
    // to allow it, we can override

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring() // bypassed by spring security
                .mvcMatchers("/css/**","/webjars/**");
    }

    // override to create test users
    // by default, Spring created a default authentication and created default user
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // not designed for production , you can configure jdbc, ldap etc
                .withUser("isaac")
                .password("{noop}qwerty12345") // {noop} spring expects each password to be encoded
                .roles("ADMIN");
    }
}
