package ma.emsi.hosp;

import ma.emsi.hosp.security.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class HospApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            UserDetails user1 = jdbcUserDetailsManager.loadUserByUsername("nouhaila");
            if(user1 == null)
                jdbcUserDetailsManager.createUser(
                        User.withUsername("nouhaila")
                                .password(passwordEncoder.encode("1234"))
                                .roles("USER")
                                .build()
                );
            UserDetails user2 = jdbcUserDetailsManager.loadUserByUsername("hamza");
            if(user2 == null)
                jdbcUserDetailsManager.createUser(
                        User.withUsername("hamza")
                                .password(passwordEncoder.encode("1234"))
                                .roles("USER")
                                .build()
                );
            UserDetails user3 = jdbcUserDetailsManager.loadUserByUsername("hamza");
            if(user3 == null)
                jdbcUserDetailsManager.createUser(
                        User.withUsername("admin")
                                .password(passwordEncoder.encode("1234"))
                                .roles("ADMIN", "USER")
                                .build()
                );

        };
    }
    //@Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
            accountService.saveRole("ADMIN");
            accountService.saveRole("USER");
            accountService.saveRole("MANAGER");
            accountService.saveUser("nouhaila", "1234", "nouhaila@gmail.com", "1234");
            accountService.saveUser("hamza", "1234", "hamza@gmail.Com", "1234");
            accountService.addRoleToUser("nouhaila", "ADMIN");
            accountService.addRoleToUser("nouhaila", "USER");
            accountService.addRoleToUser("hamza", "USER");
        };
    }

}

