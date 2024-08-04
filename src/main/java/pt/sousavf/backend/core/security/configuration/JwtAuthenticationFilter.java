package pt.sousavf.backend.core.security.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pt.sousavf.backend.core.services.api.JwtService;

import java.io.IOException;

/**
 * This class is a filter that intercepts each request once to perform JWT authentication.
 * It extends the OncePerRequestFilter class from Spring Security.
 *
 * @Component annotation indicates that this class is a Spring component.
 * @RequiredArgsConstructor annotation from Lombok generates a constructor for all final fields, with parameter order same as field order.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Getter
    private final UserDetailsService userDetailsService;

    /**
     * This method is overridden from OncePerRequestFilter class.
     * It performs the actual filtering work and is called once per request.
     *
     * @param request that is being processed
     * @param response that is being created
     * @param filterChain gives a view into the invocation chain of a filtered request
     * @throws ServletException if the processing fails for any other reason
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String userEmail = jwtService.extractUserName(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}