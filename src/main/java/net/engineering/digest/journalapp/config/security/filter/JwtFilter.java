package net.engineering.digest.journalapp.config.security.filter;

import net.engineering.digest.journalapp.config.security.UserDetailsServiceImpl;
import net.engineering.digest.journalapp.config.security.jwt.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *
 * @author abhinav.trivedi

 * This class is designed as filter which will be called in filter chanining
 * during Spring sceurity configure method call
 *
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

            // Here you would typically extract the JWT from the Authorization header,
            // validate it, and set the authentication in the security context if valid.
            // For example:
        String userName = null;
        String jwtToken = null;
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            userName= jwtUtility.extractedUsernameFromToken(jwtToken); // Implement this method to extract username from the token
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Here you would typically load the user details and set the authentication
            // For example:
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            // Validate the token and set authentication if valid
            // For example:
            if(jwtUtility.validateToken(jwtToken)){
                // Set the authentication in the security context
                // For example:
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // Just to mimic that we add  the headers that will change the HTTP Servlet Response & we can ca get headers in final response
        response.addHeader("admin", "ABHINAV");
        // This is very important step if you forget to writ ethe filter chaining then you controller will not be called and you login api itself will not work
        filterChain.doFilter(request, response);
    }
}
