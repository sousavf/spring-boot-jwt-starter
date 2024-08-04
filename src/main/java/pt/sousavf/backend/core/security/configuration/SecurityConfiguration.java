package pt.sousavf.backend.core.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * This class is responsible for the security configuration of the application.
 * It defines the security filter chain, the password encoder, the authentication provider and the authentication manager.
 * <p>
 * {@code @Configuration} annotation indicates that this class declares one or more @Bean methods and may be processed by the Spring container.
 * {@code @EnableWebSecurity} annotation enables Spring Security’s web security support.
 * {@code @RequiredArgsConstructor} annotation generates a constructor for all final fields, with parameter order same as field order.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * This method defines the security filter chain for the application.
     * It configures the HTTP security for specific http requests, session management, and adds the JWT authentication filter.
     *
     * @param http the HttpSecurity to modify
     * @return the SecurityFilterChain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/v1/auth/**", // Allow access to all authentication endpoints
                                "/swagger-ui/**", // Allow access to Swagger UI
                                "/v3/api-docs/**", // Allow access to OpenAPI docs
                                "/swagger-resources/**", // Allow access to Swagger resources
                                "/webjars/**" // Allow access to webjars used by Swagger
                        ).permitAll() // Permit access to the above endpoints
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * This method defines the password encoder for the application.
     * It uses BCryptPasswordEncoder.
     *
     * @return the PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method defines the authentication provider for the application.
     * It uses DaoAuthenticationProvider with the user details service and the password encoder.
     *
     * @return the AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtAuthenticationFilter.getUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * This method defines the authentication manager for the application.
     *
     * @param config the AuthenticationConfiguration to use
     * @return the AuthenticationManager
     * @throws Exception if an error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}