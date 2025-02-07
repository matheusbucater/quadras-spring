package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.dto.AuthRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.UsuarioCreationRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.UsuarioResponseDTO;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.exception.*;
import com.github.matheusbucater.quadras_smc.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

        public String loginUsuario(AuthRequestDTO data) {

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    data.email(),
                    data.senha()
            );

            Usuario usuario = (Usuario) this.authenticationManager.authenticate(auth).getPrincipal();

            if (!usuario.isVerfied()) {
                throw new UsuarioNotVerifiedException(data.email());
            }

            return this.tokenService.generateAuthToken(
                    usuario.getEmail(),
                    usuario.getTipoDeUsuario()
            );
        }

    public UsuarioResponseDTO registerUsuario(UsuarioCreationRequestDTO data) {

        if (this.usuarioRepository.findByEmail(data.email()) != null) {
            if (this.usuarioRepository.findByEmail(data.email()).isActive()) {
                throw new UsuarioEmailAlreadyTakenException(data.email());
            }

            this.usuarioRepository.delete(this.usuarioRepository.findByEmail(data.email()));
        }

        if (this.usuarioRepository.findByTelefone(data.telefone()) != null) {
            if (this.usuarioRepository.findByTelefone(data.telefone()).isActive()) {
                throw new UsuarioTelefoneAlreadyTakenException(data.telefone());
            }

            this.usuarioRepository.delete(this.usuarioRepository.findByTelefone(data.email()));
        }

        String senha = new BCryptPasswordEncoder().encode(data.senha());

        Usuario usuario = new Usuario(
                data.nome(),
                data.sobrenome(),
                data.email(),
                senha,
                data.descricao(),
                data.telefone()
        );

        String token = this.tokenService.generateVerifyAccountToken(data.email());

        return UsuarioResponseDTO.from(this.usuarioRepository.save(usuario), token);
    }

    public void verifyAccount(String token) {

        String email = this.tokenService.validateVerifyAccountToken(token);
        Usuario usuario = this.usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new UsuarioNotFoundException(email);
        }

        if (usuario.getVerifiedAt() != null) {
            throw new UsuarioAlreadyVerifiedException(email);
        }

        usuario.setVerifiedAt(LocalDateTime.now());

        this.usuarioRepository.save(usuario);
    }

    public void changeSenhaUsuario(String token, String senha) {

        Usuario usuarioByToken = this.usuarioRepository.findByPasswordResetToken(token);
        if (usuarioByToken == null) {
            throw new TokenValidationFailedException("Token invalido!");
        }

        String email = this.tokenService.validatePasswordResetToken(token);
        if (!usuarioByToken.getEmail().equals(email)) {
            throw new TokenValidationFailedException("Token invalido!");
        }

        Usuario usuarioByEmail = this.usuarioRepository.findByEmail(email);
        if (usuarioByEmail == null || !usuarioByEmail.isActive()) {
            throw new UsuarioNotFoundException(email);
        }

        usuarioByEmail.setSenha(senha);
        usuarioByEmail.setPasswordResetToken(null);

        this.usuarioRepository.save(usuarioByEmail);
    }

    public String requestNewPassword(String email) {

        Usuario usuario = this.usuarioRepository.findByEmail(email);

        if (usuario == null || !usuario.isActive()) {
            throw new UsuarioNotFoundException(email);
        }

        String token = this.tokenService.generatePasswordResetToken(email);

        usuario.setPasswordResetToken(token);
        this.usuarioRepository.save(usuario);

        return token;
    }

}
