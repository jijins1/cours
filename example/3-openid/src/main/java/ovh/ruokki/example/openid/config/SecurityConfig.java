package ovh.ruokki.example.openid.config;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Converter<Jwt, ? extends AbstractAuthenticationToken> authenticationConverter, ServerProperties serverProperties)
            throws Exception {
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(authenticationConverter);
        http.anonymous();
        http.cors().configurationSource(corsConfigurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        // Enable CSRF with cookie repo because of state-less session-management
        http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository());
        // Return 401 (unauthorized) instead of 302 (redirect to login) when authorization is missing or invalid
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        });
        http.authorizeRequests()
                .anyRequest().authenticated();
        return http.build();
    }
    
    public interface Jw2tAuthoritiesConverter extends Converter<Jwt, Collection<? extends GrantedAuthority>> {
    }
    
    public interface Jwt2AuthenticationConverter extends Converter<Jwt, JwtAuthenticationToken> {
    }
    
    @Bean
    public Jwt2AuthenticationConverter authenticationConverter(Jw2tAuthoritiesConverter authoritiesConverter) {
        return jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt));
    }
    
    private CorsConfigurationSource corsConfigurationSource() {
        // Very permissive CORS config...
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
