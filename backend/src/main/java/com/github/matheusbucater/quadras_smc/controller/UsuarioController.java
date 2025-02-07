package com.github.matheusbucater.quadras_smc.controller;

import com.github.matheusbucater.quadras_smc.dto.*;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllActive() {

        List<UsuarioResponseDTO> usuarios = usuarioService.getAllActiveUsuarios();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarios);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {

        List<UsuarioResponseDTO> usuarios = usuarioService.getAllUsuarios();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarios);
    }

    @GetMapping("/self")
    public ResponseEntity<UsuarioResponseDTO> getSelf(@AuthenticationPrincipal Usuario principal) {

        UsuarioResponseDTO usuario = usuarioService.getSelf(principal);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(usuario);
    }

    @GetMapping("/professores")
    public ResponseEntity<List<ProfessorResponseDTO>> getAllActiveProfessores() {

        List<ProfessorResponseDTO> professores = this.usuarioService.getAllActiveProfessores();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {

        UsuarioResponseDTO usuario = this.usuarioService.getUsuarioById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UsuarioUpdateRequestDTO usuarioAtualizado
    ) {

        UsuarioResponseDTO data = this.usuarioService.updateUsuario(id, usuarioAtualizado);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> delete(
            @PathVariable Long id
    ) {

        UsuarioResponseDTO usuarioDeletado = this.usuarioService.deleteUsuario(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioDeletado);
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<MessageDTO> hardDelete(@PathVariable Long id) {

        this.usuarioService.hardDeleteUsuario(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("Usuario deletado com sucesso"));
    }

    @PutMapping("/{id}/to-jogador")
    public ResponseEntity<MessageDTO> updateToJogador(
            @PathVariable Long id
    ) {

        UsuarioResponseDTO data = this.usuarioService.toJogador(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("Usuario atualizado com sucesso"));
    }

    @PutMapping("/{id}/to-professor")
    public ResponseEntity<MessageDTO> updateToProfessor(
            @PathVariable Long id
    ) {

        UsuarioResponseDTO data = this.usuarioService.toProfessor(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("Usuario atualizado com sucesso"));
    }

    @PutMapping("/{id}/to-admin")
    public ResponseEntity<MessageDTO> toAdmin(
            @PathVariable Long id
    ) {

        UsuarioResponseDTO data = this.usuarioService.toAdmin(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("Usuario atualizado com sucesso"));
    }

    @PutMapping("/{id}/demote-admin")
    public ResponseEntity<MessageDTO> demoteAdmin(@PathVariable Long id) {

        UsuarioResponseDTO data = this.usuarioService.demoteAdmin(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("Usuario atualizado com sucesso"));
    }
}
