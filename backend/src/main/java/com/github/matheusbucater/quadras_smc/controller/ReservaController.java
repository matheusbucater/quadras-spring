package com.github.matheusbucater.quadras_smc.controller;

import com.github.matheusbucater.quadras_smc.dto.ListaJogadoresRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.dto.ReservaRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.ReservaResponseDTO;
import com.github.matheusbucater.quadras_smc.entity.Reserva;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> getAllActive() {

        List<ReservaResponseDTO> reservas = this.reservaService.getAllActiveReservas();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservas);
    }

    @GetMapping("/quadra/{idQuadra}")
    public ResponseEntity<List<ReservaResponseDTO>> getAllActiveByIdQuadra(@PathVariable Long idQuadra) {

        List<ReservaResponseDTO> reservas = this.reservaService.getAllActiveByIdQuadra(idQuadra)
                .stream()
                .map(ReservaResponseDTO::from)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservas);
    }

    @GetMapping("/dono/{idDono}")
    public ResponseEntity<List<ReservaResponseDTO>> getAllActiveByIdDono(@PathVariable Long idDono) {

        List<ReservaResponseDTO> reservas = this.reservaService.getAllActiveByIdDono(idDono)
                .stream()
                .map(ReservaResponseDTO::from)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservas);
    }

    @GetMapping("/jogaodor/{idJogador}")
    public ResponseEntity<List<ReservaResponseDTO>> getAllActiveByIdJogador(@PathVariable Long idJogador) {

        List <ReservaResponseDTO> reservas = this.reservaService.getAllActiveByIdJogador(idJogador)
                .stream()
                .map(ReservaResponseDTO::from)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservas);
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> create(@RequestBody ReservaRequestDTO data) {

        ReservaResponseDTO reserva = this.reservaService.create(data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ReservaRequestDTO data
    ) {

        ReservaResponseDTO reserva = this.reservaService.update(id, data);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserva);
    }

    @PutMapping("/{id}/add-jogadores")
    public ResponseEntity<ReservaResponseDTO> addJogadores(
            @PathVariable Long id,
            @RequestBody ListaJogadoresRequestDTO data,
            @AuthenticationPrincipal Usuario usuario
    ) {

        ReservaResponseDTO reserva = this.reservaService.addJogadores(id, data, usuario);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserva);
    }

    @PutMapping("/{id}/to-reservado")
    public ResponseEntity<ReservaResponseDTO> toReservado(@PathVariable Long id) {

        ReservaResponseDTO reserva = this.reservaService.toReservado(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserva);
    }

    @PutMapping("/{id}/to-cancelado")
    public ResponseEntity<ReservaResponseDTO> toCancelado(@PathVariable Long id) {

        ReservaResponseDTO reserva = this.reservaService.toCancelado(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario
    ) {

        ReservaResponseDTO reserva = this.reservaService.delete(id, usuario);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserva);
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<MessageDTO> hardDelete(@PathVariable Long id) {

        this.reservaService.hardDelete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("Reserva deletada com sucesso"));
    }
}
