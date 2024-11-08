package tech.betterwith.tradingsystem.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.betterwith.tradingsystem.filters.JwtAuthenticatedFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private AuthenticationProvider authenticationProvider;
    private JwtAuthenticatedFilter jwtAuthenticatedFilter;

    @Autowired
    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticatedFilter jwtAuthenticatedFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticatedFilter = jwtAuthenticatedFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated();
                });
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthenticatedFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
