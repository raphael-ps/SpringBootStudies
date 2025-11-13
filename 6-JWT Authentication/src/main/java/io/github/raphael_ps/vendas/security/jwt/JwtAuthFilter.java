package io.github.raphael_ps.vendas.security.jwt;

import io.github.raphael_ps.vendas.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserServiceImpl userService;

    public JwtAuthFilter(JwtService jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.isTokenValid(token);

            if (isValid){
                String username = jwtService.getUserLoggedIn(token);
                UserDetails user = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken userAuth = new
                        UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                userAuth
                        .setDetails( new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(userAuth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
