package WebProject.ReRover.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin().permitAll()
            .and()
            .logout()
                .logoutUrl("/api/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
        return http.build();
    }
}