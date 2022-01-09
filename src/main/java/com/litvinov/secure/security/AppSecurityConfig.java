package com.litvinov.secure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.litvinov.secure.security.UserRole.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*","login").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses",true)
                    .passwordParameter("password")//данные для файла login.html если нужно поменять атрибут в html
                    .usernameParameter("username")
                .and()
                .rememberMe()
                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                .key("somesecurity")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessUrl("/login");

    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails first = User.builder()
                .username("first")
                .password(passwordEncoder.encode("password"))
                .authorities(STUDENT.getGrantedAuthority())
                //.roles("STUDENT")
                .roles(STUDENT.name())
                .build();

        var linda = User.builder().username("linda")
                .password(passwordEncoder.encode("password"))
                //.roles("ADMIN")
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthority())
                .build();

        var tom = User.builder().username("tom")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMINTRAIN.name())
                .authorities(ADMINTRAIN.getGrantedAuthority())
                .build();
        return new InMemoryUserDetailsManager(first, linda, tom);
    }
}
