package com.jee.hopital;

import com.jee.hopital.entities.Patient;
import com.jee.hopital.repository.PatientRepository;
import com.jee.hopital.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HopitalApplication {

    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(HopitalApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            patientRepository.save(new Patient(null, "Saad", new Date(), false,123));
            patientRepository.save(new Patient(null, "Yassine", new Date(), true,38));
            patientRepository.save(new Patient(null, "Inas", new Date(), true,55));


        };
    }


    @Bean
    public CommandLineRunner commandLineRunner(AccountService accountService) {
        return args -> {
            accountService.addNewRole("ADMIN");
            accountService.addNewRole("USER");
            accountService.addNewUser("user1","user1@gmail.com","1234","1234");
            accountService.addNewUser("user2","user2@gmail.com","1234","1234");
            accountService.addNewUser("admin","admin@gmail.com","admin","admin");

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","ADMIN");
        };
    }

//    @Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {

            UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user11");
            if (u1==null) {
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user11").password(passwordEncoder.encode("1234")).roles("USER").build()
            );}
            UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user22");
            if (u2==null) {
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user22").password(passwordEncoder.encode("1234")).roles("USER").build()
            );}
            UserDetails u3 = jdbcUserDetailsManager.loadUserByUsername("admin2");
            if (u2==null) {
            jdbcUserDetailsManager.createUser(
                    User.withUsername("admin2").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").build()
            );}
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
