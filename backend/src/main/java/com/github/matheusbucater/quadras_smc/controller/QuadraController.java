package com.github.matheusbucater.quadras_smc.controller;

import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.dto.QuadraRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.QuadraResponseDTO;
import com.github.matheusbucater.quadras_smc.service.QuadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quadras")
public class QuadraController {

    @Autowired
    private QuadraService quadraService;

    @GetMapping
    public ResponseEntity<List<QuadraResponseDTO>> getAllActive() {

        List<QuadraResponseDTO> quadras = this.quadraService.getAllActiveQuadras();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(quadras);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuadraResponseDTO>> getAll() {
        List<QuadraResponseDTO> quadras = this.quadraService.getAllQuadras();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(quadras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuadraResponseDTO> getById(@PathVariable Long id) {

        QuadraResponseDTO quadra = this.quadraService.getQuadraById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(quadra);
    }

    @PostMapping
    public ResponseEntity<QuadraResponseDTO> create(@RequestBody QuadraRequestDTO quadra) {

        QuadraResponseDTO novaQuadra = this.quadraService.createQuadra(quadra);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaQuadra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuadraResponseDTO> update(@PathVariable Long id, @RequestBody QuadraRequestDTO data) {

        QuadraResponseDTO quadra  = this.quadraService.updateQuadra(id, data);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(quadra);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QuadraResponseDTO> delete(@PathVariable Long id) {

        QuadraResponseDTO quadraDeletada = this.quadraService.deleteQuadra(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(quadraDeletada);
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<MessageDTO> hardDelete(@PathVariable Long id) {

        this.quadraService.hardDeleteQuadra(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new MessageDTO("Quadra deletada com sucesso"));
    }
}
