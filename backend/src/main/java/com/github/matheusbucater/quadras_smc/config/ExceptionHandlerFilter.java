package com.github.matheusbucater.quadras_smc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.exception.TokenValidationFailedException;
import com.github.matheusbucater.quadras_smc.exception.UsuarioNotVerifiedException;
import com.github.matheusbucater.quadras_smc.exception.UsuarioSoftDeletedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenValidationFailedException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(
                    new ErrorMessageDTO(e.toString(), e.getMessage())
            ));
        } catch (UsuarioSoftDeletedException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(
                    new ErrorMessageDTO(e.toString(), e.getMessage())
            ));
        }

    }
}
