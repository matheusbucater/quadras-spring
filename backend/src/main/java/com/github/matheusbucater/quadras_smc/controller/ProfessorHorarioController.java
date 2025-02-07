package com.github.matheusbucater.quadras_smc.controller;

import com.github.matheusbucater.quadras_smc.dto.HorarioRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.HorarioResponseDTO;
import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.service.ProfessorHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor-horarios")
public class ProfessorHorarioController {

    @Autowired
    private ProfessorHorarioService professorHorarioService;

    @GetMapping("/{idProfessor}")
    public ResponseEntity<List<HorarioResponseDTO>> getAllActiveByIdProfessor(@PathVariable Long idProfessor) {

        List<HorarioResponseDTO> horarios = this.professorHorarioService.getAllActiveHorarioByIdProfessor(idProfessor)
                .stream()
                .map(HorarioResponseDTO::from)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(horarios);
    }

    @PostMapping("/{idProfessor}")
    public ResponseEntity<HorarioResponseDTO> create(
            @PathVariable Long idProfessor,
            @RequestBody HorarioRequestDTO data
    ) {

        HorarioResponseDTO horario = this.professorHorarioService.createHorario(idProfessor, data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(horario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponseDTO> update(
            @PathVariable Long id,
            @RequestBody HorarioRequestDTO data
    ) {

        HorarioResponseDTO horario = this.professorHorarioService.updateHorarioById(id, data);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(horario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HorarioResponseDTO> delete(@PathVariable Long id) {

        HorarioResponseDTO horario = this.professorHorarioService.deleteHorarioById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(horario);
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<MessageDTO> hardDelete(@PathVariable Long id) {

        this.professorHorarioService.hardDeleteHorarioById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("ProfessorHorario deletado com sucesso"));
    }

}
