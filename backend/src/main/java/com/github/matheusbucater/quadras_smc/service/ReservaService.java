package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.dto.ListaJogadoresRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.ReservaRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.ReservaResponseDTO;
import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.entity.Reserva;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.enums.EstadoDaReserva;
import com.github.matheusbucater.quadras_smc.enums.TipoDeReserva;
import com.github.matheusbucater.quadras_smc.enums.TipoDeUsuario;
import com.github.matheusbucater.quadras_smc.exception.QuadraNotFoundException;
import com.github.matheusbucater.quadras_smc.exception.ReservaNotFoundException;
import com.github.matheusbucater.quadras_smc.exception.UsuarioForbiddenException;
import com.github.matheusbucater.quadras_smc.exception.UsuarioNotFoundException;
import com.github.matheusbucater.quadras_smc.repository.QuadraRepository;
import com.github.matheusbucater.quadras_smc.repository.ReservaRepository;
import com.github.matheusbucater.quadras_smc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReservaService {

    @Autowired
    private QuadraRepository quadraRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ReservaRepository reservaRepository;

    public List<ReservaResponseDTO> getAllActiveReservas() {

        List<Reserva> reservas = this.reservaRepository.getAllActive();

        return reservas.stream().map(ReservaResponseDTO::from).toList();
    }

    public List<Reserva> getAllActiveByIdQuadra(Long idQuadra) {

        Quadra quadra  = this.quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new QuadraNotFoundException(idQuadra));

        return this.reservaRepository.getAllActiveByQuadra(quadra);
    }

    @PreAuthorize("#idDono == authentication.principal.id")
    public List<Reserva> getAllActiveByIdDono(Long idDono) {

        Usuario dono = this.usuarioRepository.findById(idDono)
                .orElseThrow(() -> new UsuarioNotFoundException(idDono));

        return this.reservaRepository.getAllActiveByDonoDaReserva(dono);
    }

    @PreAuthorize("#idJogador == authentication.principal.id")
    public List<Reserva> getAllActiveByIdJogador(Long idJogador) {

        Usuario jogador = this.usuarioRepository.findById(idJogador)
                .orElseThrow(() -> new UsuarioNotFoundException(idJogador));

        return this.reservaRepository.getAllActiveByJogadoresContains(jogador);
    }

    public ReservaResponseDTO create(ReservaRequestDTO data) {

        Usuario dono = this.usuarioRepository.findById(data.id_dono())
                .orElseThrow(() -> new UsuarioNotFoundException(data.id_dono()));

        Quadra quadra = this.quadraRepository.findById(data.id_quadra())
                .orElseThrow(() -> new QuadraNotFoundException(data.id_quadra()));

        List<Usuario> jogadores = new ArrayList<>();
        jogadores.add(dono);

        Reserva reserva = new Reserva(
                dono,
                quadra,
                jogadores,
                data.tipo_de_reserva(),
                data.num_jogadores(),
                data.descricao(),
                data.eh_publico(),
                EstadoDaReserva.PENDENTE,
                data.dt_reserva(),
                data.hr_inicio(),
                data.hr_fim()
        );

        return ReservaResponseDTO.from(this.reservaRepository.save(reserva));
    }

    @PreAuthorize("#data.id_dono() == authentication.principal.id")
    public ReservaResponseDTO update(Long id, ReservaRequestDTO data) {

        Reserva reserva = this.reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNotFoundException(id));

        if (data.id_dono() != null) {
            Usuario dono = this.usuarioRepository.findById(data.id_dono())
                    .orElseThrow(() -> new UsuarioNotFoundException(data.id_dono()));
            reserva.setDonoDaReserva(dono);
        }

        if (data.id_quadra() != null) {
            Quadra quadra = this.quadraRepository.findById(data.id_quadra())
                    .orElseThrow(() -> new QuadraNotFoundException(data.id_quadra()));
            reserva.setQuadra(quadra);
        }

        if (data.tipo_de_reserva() != null) reserva.setTipoDeReserva(data.tipo_de_reserva());
        if (data.num_jogadores() != null)   reserva.setNumJogadores(data.num_jogadores());
        if (data.descricao() != null)       reserva.setDescricao(data.descricao());
        if (data.eh_publico() != null)      reserva.setEhPublica(data.eh_publico());
        if (data.dt_reserva() != null)      reserva.setDtReserva(data.dt_reserva());
        if (data.hr_inicio() != null)       reserva.setHrInicio(data.hr_inicio());
        if (data.hr_fim() != null)          reserva.setHrFim(data.hr_fim());


        return ReservaResponseDTO.from(this.reservaRepository.save(reserva));
    }

    public ReservaResponseDTO addJogadores(Long id, ListaJogadoresRequestDTO data, Usuario usuario) {

        Reserva reserva = this.reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNotFoundException(id));

        if (!Objects.equals(usuario.getId(), reserva.getDonoDaReserva().getId())) {
            throw new UsuarioForbiddenException(usuario.getId());
        }

        data.jogadores().forEach(id_jogador -> {
            Usuario jogador = this.usuarioRepository.findById(id)
                    .orElseThrow(() -> new UsuarioNotFoundException(id));
            reserva.getJogadores().add(jogador);
        });

        return ReservaResponseDTO.from(this.reservaRepository.save(reserva));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ReservaResponseDTO toReservado(Long id) {

        Reserva reserva = this.reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNotFoundException(id));

        reserva.setEstadoDaReseva(EstadoDaReserva.RESERVADO);

        return ReservaResponseDTO.from(this.reservaRepository.save(reserva));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ReservaResponseDTO toCancelado(Long id) {

        Reserva reserva = this.reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNotFoundException(id));

        reserva.setEstadoDaReseva(EstadoDaReserva.CANCELADO);

        return ReservaResponseDTO.from(this.reservaRepository.save(reserva));
    }

    public ReservaResponseDTO delete(Long id, Usuario usuario) {

        Reserva reserva = this.reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNotFoundException(id));

        if (
                !Objects.equals(usuario.getId(), reserva.getDonoDaReserva().getId())
                && usuario.getTipoDeUsuario() != TipoDeUsuario.ADMINISTRADOR
                && usuario.getTipoDeUsuario() != TipoDeUsuario.SUPERVISOR
        ) {

            throw new UsuarioForbiddenException(usuario.getId());
        }

        reserva.setDeletedAt(LocalDateTime.now());

        return ReservaResponseDTO.from(this.reservaRepository.save(reserva));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    public void hardDelete(Long id) {

        this.reservaRepository.deleteById(id);
    }
}
