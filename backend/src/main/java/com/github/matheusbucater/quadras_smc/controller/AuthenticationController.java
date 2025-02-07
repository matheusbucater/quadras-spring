package com.github.matheusbucater.quadras_smc.controller;

import com.github.matheusbucater.quadras_smc.dto.*;
import com.github.matheusbucater.quadras_smc.service.TokenService;
import com.github.matheusbucater.quadras_smc.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthRequestDTO data) {

        String token = this.authenticationService.loginUsuario(data);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new TokenDTO(token));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refresh(@RequestHeader("Authorization") String token) {

        String novoToken = this.tokenService.refreshAuthToken(token.replace("Bearer ", ""));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new TokenDTO(novoToken));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody UsuarioCreationRequestDTO data) {

        UsuarioResponseDTO usuario = this.authenticationService.registerUsuario(data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuario);
    }

    @PutMapping("/verify-account")
    public ResponseEntity<MessageDTO> verifyAccount(@RequestBody TokenDTO data) {

        this.authenticationService.verifyAccount(data.token());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageDTO("Usuário verificado com sucesso"));
    }

    @PostMapping("/request-new-password")
    public ResponseEntity<TokenDTO> requestNewPassword(@RequestBody EmailAddressDTO data) {

        String token = this.authenticationService.requestNewPassword(data.email());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new TokenDTO(token));
    }

    @PutMapping("/change-password")
    public ResponseEntity<MessageDTO> changePassword(@RequestBody MudarSenhaDTO novaSenha) {

        String senha = new BCryptPasswordEncoder().encode(novaSenha.nova_senha());

        this.authenticationService.changeSenhaUsuario(novaSenha.token(), senha);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("Senha alterada com sucesso"));
    }

}
