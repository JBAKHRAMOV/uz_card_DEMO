package com.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // authentication
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}adminjon").roles("ADMIN")
                .and()
                .withUser("profile").password("{noop}profilejon").roles("PROFILE")
                .and()
                .withUser("bank").password("{noop}bankjon").roles("BANK");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorization
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/client/bank/**").hasAnyRole("BANK")
                .antMatchers("/client/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/card/public/**").permitAll()
                .antMatchers("/card/bank/**").hasAnyRole("BANK")
                .anyRequest().authenticated().and().httpBasic();
        http.cors().disable().csrf().disable();
    }
}
