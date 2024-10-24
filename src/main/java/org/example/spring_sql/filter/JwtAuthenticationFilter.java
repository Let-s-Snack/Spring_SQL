package org.example.spring_sql.filter;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.spring_sql.service.AdmCustomDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AdmCustomDetailsService admCustomDetailsService;
    private final SecretKey secretKey;

    public JwtAuthenticationFilter(AdmCustomDetailsService admCustomDetailsService, SecretKey secretKey) {
        this.admCustomDetailsService = admCustomDetailsService;
        this.secretKey = secretKey;
    }

    @Override //Recebe o token, verifica se ele está diferente de nulo e se inicia com bearer, tirando o bearer e verificando se o token está autorizado
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {//Recebe a requisição e verifica se o usuário tem o token ou nn
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String email = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(authHeader)
                    .getBody()
                    .getSubject();

            if (email != null) {
                UserDetails userDetails = admCustomDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        Collections.emptyList()
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Usuário não autorizado!");
        }

        filterChain.doFilter(request, response);
    }
}