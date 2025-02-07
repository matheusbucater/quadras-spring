package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.dto.ProfessorResponseDTO;
import com.github.matheusbucater.quadras_smc.dto.UsuarioResponseDTO;
import com.github.matheusbucater.quadras_smc.dto.UsuarioUpdateRequestDTO;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.enums.TipoDeUsuario;
import com.github.matheusbucater.quadras_smc.exception.UsuarioForbiddenException;
import com.github.matheusbucater.quadras_smc.exception.UsuarioNotFoundException;
import com.github.matheusbucater.quadras_smc.repository.UsuarioRepository;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    public List<UsuarioResponseDTO> getAllActiveUsuarios() {

        List<Usuario> usuarios = this.usuarioRepository.findAllActive();
        return usuarios.stream().map(UsuarioResponseDTO::from).collect(Collectors.toList());
    }

    public List<ProfessorResponseDTO> getAllActiveProfessores() {

        List<Usuario> professores = this.usuarioRepository.findAllActiveByTipoDeUsuario(TipoDeUsuario.PROFESSOR.toString());

        return professores.stream().map(ProfessorResponseDTO::from).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    public List<UsuarioResponseDTO> getAllUsuarios() {

        List<Usuario> usuarios = this.usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioResponseDTO::from).collect(Collectors.toList());
    }

    public UsuarioResponseDTO getUsuarioById(Long id) {

        return this.usuarioRepository
                .findById(id)
                .map(UsuarioResponseDTO::from)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    public UsuarioResponseDTO getSelf(Usuario usuario) {

        return UsuarioResponseDTO.from(usuario);
    }

    @PreAuthorize("#id == authentication.principal.id || hasRole('SUPERVISOR')")
    public UsuarioResponseDTO updateUsuario(Long id, UsuarioUpdateRequestDTO usuarioAtualizado) {

        return this.usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(
                            usuarioAtualizado.nome() != null ? usuarioAtualizado.nome() : usuario.getNome()
                    );
                    usuario.setSobrenome(
                            usuarioAtualizado.sobrenome() != null ? usuarioAtualizado.sobrenome() : usuario.getSobrenome()
                    );
                    usuario.setDescricao(
                            usuarioAtualizado.descricao() != null ? usuarioAtualizado.descricao() : usuario.getDescricao()
                    );
                    usuario.setEmail(
                            usuarioAtualizado.email() != null ? usuarioAtualizado.email() : usuario.getEmail()
                    );
                    usuario.setTelefone(
                            usuarioAtualizado.telefone() != null ? usuarioAtualizado.telefone() : usuario.getTelefone()
                    );
                    return UsuarioResponseDTO.from(this.usuarioRepository.save(usuario));
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    @PreAuthorize("#id == authentication.principal.id || hasRole('SUPERVISOR')")
    public UsuarioResponseDTO deleteUsuario(Long id) {

        return this.usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setDeletedAt(LocalDateTime.now());
                    return UsuarioResponseDTO.from(this.usuarioRepository.save(usuario));
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    public void hardDeleteUsuario(Long id) {

        this.usuarioRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioResponseDTO toJogador(Long id) {

        return UsuarioResponseDTO.from(this.usuarioRepository.findById(id)
                .map(usuario -> {
                    if (usuario.getTipoDeUsuario() == TipoDeUsuario.SUPERVISOR
                        || usuario.getTipoDeUsuario() == TipoDeUsuario.ADMINISTRADOR
                    ) {
                        throw new UsuarioForbiddenException(id);
                    }
                    usuario.setTipoDeUsuario(TipoDeUsuario.JOGADOR);
                    return this.usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id))
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioResponseDTO toProfessor(Long id) {

        return UsuarioResponseDTO.from(this.usuarioRepository.findById(id)
                .map(usuario -> {
                    if (usuario.getTipoDeUsuario() == TipoDeUsuario.SUPERVISOR
                            || usuario.getTipoDeUsuario() == TipoDeUsuario.ADMINISTRADOR
                    ) {
                        throw new UsuarioForbiddenException(id);
                    }
                    usuario.setTipoDeUsuario(TipoDeUsuario.PROFESSOR);
                    return this.usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id))
        );
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    public UsuarioResponseDTO toAdmin(Long id) {

        return UsuarioResponseDTO.from(this.usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setTipoDeUsuario(TipoDeUsuario.ADMINISTRADOR);
                    return this.usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id))
        );
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    public UsuarioResponseDTO demoteAdmin(Long id) {

        return UsuarioResponseDTO.from(this.usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setTipoDeUsuario(TipoDeUsuario.JOGADOR);
                    return this.usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id))
        );
    }
}
