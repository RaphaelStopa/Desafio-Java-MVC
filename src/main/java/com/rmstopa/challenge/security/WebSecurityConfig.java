package com.rmstopa.challenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.rmstopa.challenge.security.AuthoritiesConstants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  ImplementsUserDetailsService implementsUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests()
                .antMatchers("/daily").hasAuthority(MANAGER)
                .antMatchers("/daily/**").hasAuthority(MANAGER)
                .antMatchers("/employee").hasAuthority(MANAGER)
                .antMatchers("/employee/**").hasAuthority(MANAGER)
                .antMatchers("/group").hasAuthority(MANAGER)
                .antMatchers("/group/**").hasAuthority(MANAGER)
                .antMatchers("/module").hasAuthority(MANAGER)
                .antMatchers("/module/**").hasAuthority(MANAGER)
                .antMatchers("/start-program").hasAuthority(MANAGER)
                .antMatchers("/start-program/**").hasAuthority(MANAGER)
                .antMatchers("/technology").hasAuthority(MANAGER)
                .antMatchers("/technology/**").hasAuthority(MANAGER)
                .antMatchers("/project").hasAuthority(MANAGER)
                .antMatchers("/project/**").hasAuthority(MANAGER)
                .antMatchers("/scrum").hasAuthority(SCRUM_MASTER)
                .antMatchers("/scrum/**").hasAuthority(SCRUM_MASTER)
                .antMatchers("/").authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //This encryption is only symbolic. It doesn't actually work. In a real project you would have to use a key
        auth.userDetailsService(implementsUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers( "/style/**").antMatchers("/img/**");
    }

}
