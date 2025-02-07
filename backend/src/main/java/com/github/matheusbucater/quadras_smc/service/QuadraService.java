package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.dto.QuadraRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.QuadraResponseDTO;
import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.exception.QuadraNotFoundException;
import com.github.matheusbucater.quadras_smc.repository.QuadraHorarioRepository;
import com.github.matheusbucater.quadras_smc.repository.QuadraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuadraService {

    @Autowired
    private QuadraRepository quadraRepository;
    @Autowired
    private QuadraHorarioRepository quadraHorarioRepository;

    public List<QuadraResponseDTO> getAllActiveQuadras() {

        return this.quadraRepository.findAllActive().stream()
                .map(QuadraResponseDTO::from)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<QuadraResponseDTO> getAllQuadras() {

        return this.quadraRepository.findAll().stream()
                .map(QuadraResponseDTO::from)
                .collect(Collectors.toList());
    }

    public QuadraResponseDTO getQuadraById(Long id) {

        return this.quadraRepository.findById(id)
                .map(QuadraResponseDTO::from)
                .orElseThrow(() -> new QuadraNotFoundException(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public QuadraResponseDTO createQuadra(QuadraRequestDTO quadra) {

        Quadra novaQuadra = new Quadra(
                quadra.nome(),
                quadra.descricao(),
                quadra.eh_coberta(),
                quadra.tipo_de_quadra(),
                quadra.estado_da_quadra(),
                quadra.imgurl()
        );

        return QuadraResponseDTO.from(this.quadraRepository.save(novaQuadra));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public QuadraResponseDTO updateQuadra(Long id, QuadraRequestDTO data) {

        return this.quadraRepository.findById(id)
                .map(quadra -> {
                    quadra.setNome(
                        data.nome() != null ? data.nome() : quadra.getNome()
                    );
                    quadra.setDescricao(
                            data.descricao() != null ? data.descricao() : quadra.getDescricao()
                    );
                    quadra.setImgurl(
                            data.imgurl() != null ? data.imgurl() : quadra.getImgurl()
                    );
                    quadra.setEhCoberta(
                            data.eh_coberta() != null ? data.eh_coberta() : quadra.getEhCoberta()
                    );
                    quadra.setTipoDeQuadra(
                            data.tipo_de_quadra() != null ? data.tipo_de_quadra() : quadra.getTipoDeQuadra()
                    );
                    quadra.setEstadoDaQuadra(
                            data.estado_da_quadra() != null ? data.estado_da_quadra() : quadra.getEstadoDaQuadra()
                    );
                    return QuadraResponseDTO.from(this.quadraRepository.save(quadra));
                })
                .orElseThrow(() -> new QuadraNotFoundException(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public QuadraResponseDTO deleteQuadra(Long id) {

        return this.quadraRepository.findById(id)
                .map(quadra -> {
                    quadra.setDeletedAt(LocalDateTime.now());
                    return QuadraResponseDTO.from(this.quadraRepository.save(quadra));
                })
                .orElseThrow(() -> new QuadraNotFoundException(id));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    public void hardDeleteQuadra(Long id) {

        this.quadraRepository.deleteById(id);
    }
}
