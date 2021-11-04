package com.tracom.mop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomEmployeeDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Configure the login and log out for the application
            http.authorizeRequests()
                .antMatchers("/login","/home", "/css/**","/js/**", "/images/**", "/vendor/**", "/assets/**").permitAll()
                .antMatchers("/edit_user/**", "/delete_user/**").hasRole("admin")
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/home")
                                .loginProcessingUrl("/perform-login")
                                .usernameParameter("email").passwordParameter("password")
                                .failureUrl("/login?error=true")
                )
                .logout().logoutSuccessUrl("/logout").permitAll()
                    .and()
                    .exceptionHandling().accessDeniedPage("/403");
    }

}

