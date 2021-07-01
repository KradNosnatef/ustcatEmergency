package com.ustcat.ustcatEmergency.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/Xapi/regist", "/Xapi/getUsername","/Xapi/adminCheck","/Xapi/Bill/listAllDebts","/Xapi/Bill/sumDebts").permitAll().and().authorizeRequests()
                .antMatchers("/Xapi/admin/*", "/Xapi/admin").access("hasRole('ADMIN') and hasRole('USER')").anyRequest().authenticated().and()
                .formLogin().and().httpBasic();

        http.csrf().disable().authorizeRequests();
    }
}
