package ovh.ruokki.example.jwt.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    
    public static final String SECRET = "Une cle secrete";// Mettre la cle en constante c'est mal !!
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        
        String header = request.getHeader(HEADER_STRING);
        if (header == null) {
            log.info("Pas de header");
            filterChain.doFilter(request, response);
        } else {
            var token = header.replace(TOKEN_PREFIX, "");
            
            log.info("Verification du token {}", token);
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer("Polytech")
                    .build(); //Reusable verifier instance
            try {
                DecodedJWT jwt = verifier.verify(token);
                var user = jwt.getSubject();
                SecurityContextHolder.createEmptyContext().setAuthentication(new AnonymousAuthenticationToken(user, user, List.of(new SimpleGrantedAuthority("User"))));
                
            } catch (JWTVerificationException jwtVerificationException) {
                log.error("Le token est invalide", jwtVerificationException);
                filterChain.doFilter(request, response);
            }
        }
    }
    
}
