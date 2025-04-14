package com.jee.hopital.security;

import com.jee.hopital.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailServiceImpl userDetailService;


//    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);
    }


//    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user").password(passwordEncoder.encode("password")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("password")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/webjars/**").permitAll()
//                                .requestMatchers("/user/**").hasRole("USER")
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailService)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/notAuthorized")
                )
                .formLogin(formLogin ->
                        formLogin.loginPage("/login").defaultSuccessUrl("/" , true).permitAll())
                .httpBasic(Customizer.withDefaults());;
        return httpSecurity.build();
    }
}
