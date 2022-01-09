package com.litvinov.secure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.litvinov.secure.security.UserPermission.COURSE_WRITE;
import static com.litvinov.secure.security.UserRole.*;

@EnableWebSecurity
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
                .authorizeHttpRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE,"/manage/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/manage/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/manage/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/manage/api/**").hasAnyRole(ADMIN.name(), ADMINTRAIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
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
