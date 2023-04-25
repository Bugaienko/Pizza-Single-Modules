package balu.pizzarest.pizzaproject.config;

import balu.pizzarest.pizzaproject.security.JwtUtil;
import balu.pizzarest.pizzaproject.services.PersonDetailService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sergii Bugaienko
 */

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final PersonDetailService personDetailService;

    @Autowired
    public JWTFilter(JwtUtil jwtUtil, PersonDetailService personDetailService) {
        this.jwtUtil = jwtUtil;
        this.personDetailService = personDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token in Bearer Header");
            } else {
                try {


                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = personDetailService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid JWT Token");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
