package com.travel.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {})
  .csrf(csrf -> csrf.disable())
  .httpBasic(b -> b.disable())
  .formLogin(f -> f.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

    // ✅ allow these explicitly
    .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
    .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
    .requestMatchers(HttpMethod.POST, "/api/auth/forgot-password").permitAll()
    .requestMatchers(HttpMethod.POST, "/api/auth/reset-password").permitAll()

    // ✅ allow public GET me? (NO) keep protected:
    .requestMatchers("/api/auth/change-password").authenticated()
    .requestMatchers("/api/auth/me").authenticated()

    // public browse
    .requestMatchers("/api/hotels/**").permitAll()
    .requestMatchers("/api/destinations/**").permitAll()
    .requestMatchers("/api/packs/**").permitAll()

    // reservations
    .requestMatchers(HttpMethod.POST, "/api/reservations").authenticated()
    .requestMatchers(HttpMethod.GET, "/api/reservations/me").authenticated()
    .requestMatchers(HttpMethod.GET, "/api/reservations/*/invoice").authenticated()
    .requestMatchers(HttpMethod.GET, "/api/reservations/*/invoice.pdf").authenticated()

    .requestMatchers(HttpMethod.POST, "/api/destination-reservations").authenticated()
    .requestMatchers(HttpMethod.GET, "/api/destination-reservations/me").authenticated()
    .requestMatchers(HttpMethod.GET, "/api/destination-reservations/*/invoice").authenticated()

    .requestMatchers(HttpMethod.POST, "/api/pack-reservations").authenticated()
    .requestMatchers(HttpMethod.GET, "/api/pack-reservations/me").authenticated()
    .requestMatchers(HttpMethod.GET, "/api/pack-reservations/*/invoice").authenticated()

    .requestMatchers("/api/admin/reservations/**").permitAll()

    .anyRequest().authenticated()
)

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            System.out.println("✅ SecurityConfig loaded");

        return http.build();
        
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    // ✅ allow both
    config.setAllowedOriginPatterns(List.of(
        "http://localhost:4200",
        "http://127.0.0.1:4200"
    ));

    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setExposedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}
}
