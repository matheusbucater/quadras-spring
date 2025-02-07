package com.github.matheusbucater.quadras_smc.controller;

import com.github.matheusbucater.quadras_smc.dto.HorarioResponseDTO;
import com.github.matheusbucater.quadras_smc.dto.HorarioRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.service.QuadraHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quadra-horarios")
public class QuadraHorarioController {

    @Autowired
    private QuadraHorarioService quadraHorarioService;

    @GetMapping("/{idQuadra}")
    public ResponseEntity<List<HorarioResponseDTO>> getAllActiveByIdQuadra(@PathVariable Long idQuadra) {

        List<HorarioResponseDTO> horarios = this.quadraHorarioService.getAllActiveQuadraHorarioByIdQuadra(idQuadra)
                .stream()
                .map(HorarioResponseDTO::from)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(horarios);
    }

    @PostMapping("/{idQuadra}")
    public ResponseEntity<HorarioResponseDTO> create(
            @PathVariable Long idQuadra,
            @RequestBody HorarioRequestDTO data
    ) {

        HorarioResponseDTO horario = this.quadraHorarioService.createQuadraHorario(idQuadra, data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(horario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponseDTO> update(
            @PathVariable Long id,
            @RequestBody HorarioRequestDTO data
    ) {

        HorarioResponseDTO horario = this.quadraHorarioService.updateQuadraHorarioById(id, data);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(horario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HorarioResponseDTO> delete(@PathVariable Long id) {

        HorarioResponseDTO horario = this.quadraHorarioService.deleteQuadraHorarioById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(horario);
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<MessageDTO> hardDelete(@PathVariable Long id) {

        this.quadraHorarioService.hardDeleteQuadraHorarioById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("QuadraHorario deletado com sucesso"));
    }

}
