package com.practice.springsecurity.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// we can configure multiple filter chains for requests in our application
// useful to implement different authentication strategies in our application
@Configuration
// default order is 100 and two filter chains cannot have the same order
// used by FilterChainProxy to evaluate the order in which it should evaluate the two filter chains
// this means that it will be evaluated first
@Order(99)
public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.mvcMatcher("/support/admin") // all requests to "/support/admin" will go through this security configuration
        .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")
                .and()
                .formLogin();

    }
}
