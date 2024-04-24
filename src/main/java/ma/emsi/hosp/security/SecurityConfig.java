package ma.emsi.hosp.security;

import lombok.AllArgsConstructor;
import ma.emsi.hosp.security.services.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userDetailsService;


    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(
                (form) -> form.defaultSuccessUrl("/", true).
                        loginPage("/login").
                        permitAll()

        );
        http.rememberMe(
                (rememberMe) -> rememberMe.key("remember-me").
                        rememberMeServices(new TokenBasedRememberMeServices("remember-me"))

        );
        http.authorizeHttpRequests(
                (auth) -> auth
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/webjars/**").permitAll()
        );
        http.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated()

        );
        http.exceptionHandling(
                (exception) -> exception.accessDeniedPage("/notAuthorized")
        );
        http.userDetailsService(userDetailsService);
        return http.build();

    }
}

