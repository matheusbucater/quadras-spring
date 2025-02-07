package com.github.matheusbucater.quadras_smc.config;

import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.exception.UsuarioNotVerifiedException;
import com.github.matheusbucater.quadras_smc.exception.UsuarioSoftDeletedException;
import com.github.matheusbucater.quadras_smc.repository.UsuarioRepository;
import com.github.matheusbucater.quadras_smc.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.recoverToken(request);
        if (token != null) {
            String email = this.tokenService.extractEmailFromAuthToken(token);
            Usuario usuario = this.usuarioRepository.findByEmail(email);

            if (!usuario.isActive()) {
                throw new UsuarioSoftDeletedException(usuario.getEmail());
            }

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    )
            );
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        } else {
            return authHeader.replace("Bearer ", "");
        }
    }


}
