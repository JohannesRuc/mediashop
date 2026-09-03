package de.mediashop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Stateless Bearer-Token-API: keine Session, keine Cookies -> CSRF nicht anwendbar
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/catalog/**").permitAll()
                // Webhook des Payment-Providers, kein Bearer-Token vorhanden
                .requestMatchers("/payments/callback").permitAll()
                // Management-Port ist laut Deployment nur clusterintern erreichbar
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/orders/**", "/invoices/**").authenticated()
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
