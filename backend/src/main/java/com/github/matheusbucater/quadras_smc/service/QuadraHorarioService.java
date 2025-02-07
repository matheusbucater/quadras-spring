package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.dto.HorarioResponseDTO;
import com.github.matheusbucater.quadras_smc.dto.HorarioRequestDTO;
import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.entity.QuadraHorario;
import com.github.matheusbucater.quadras_smc.exception.QuadraNotFoundException;
import com.github.matheusbucater.quadras_smc.exception.QuadraHorarioNotFoundException;
import com.github.matheusbucater.quadras_smc.repository.QuadraHorarioRepository;
import com.github.matheusbucater.quadras_smc.repository.QuadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuadraHorarioService {

    @Autowired
    private QuadraRepository quadraRepository;

    @Autowired
    private QuadraHorarioRepository quadraHorarioRepository;

    public List<QuadraHorario> getAllActiveQuadraHorarioByIdQuadra(Long idQuadra) {

        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new QuadraNotFoundException(idQuadra));

        return this.quadraHorarioRepository.findAllActiveByQuadra(quadra);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public HorarioResponseDTO createQuadraHorario(Long idQuadra, HorarioRequestDTO horario) {

        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new QuadraNotFoundException(idQuadra));

        QuadraHorario quadraHorario = new QuadraHorario(
                quadra,
                horario.dia_da_semana(),
                horario.hr_fim(),
                horario.hr_inicio()
        );

        return HorarioResponseDTO.from(this.quadraHorarioRepository.save(quadraHorario));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public HorarioResponseDTO updateQuadraHorarioById(Long id, HorarioRequestDTO data) {


        return this.quadraHorarioRepository.findById(id)
                .map(quadraHorario -> {
                    quadraHorario.setDiaDaSemana(
                            data.dia_da_semana() != null ? data.dia_da_semana() : quadraHorario.getDiaDaSemana()
                    );
                    quadraHorario.setHrInicio(
                            data.hr_inicio() != null ? data.hr_inicio() : quadraHorario.getHrInicio()
                    );
                    quadraHorario.setHrFim(
                            data.hr_fim() != null ? data.hr_fim() : quadraHorario.getHrFim()
                    );
                    return HorarioResponseDTO.from(this.quadraHorarioRepository.save(quadraHorario));
                })
                .orElseThrow(() -> new QuadraHorarioNotFoundException(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public HorarioResponseDTO deleteQuadraHorarioById(Long id) {

        return this.quadraHorarioRepository.findById(id)
                .map(horario -> {
                    horario.setDeletedAt(LocalDateTime.now());
                    return HorarioResponseDTO.from(this.quadraHorarioRepository.save(horario));
                })
                .orElseThrow(() -> new QuadraHorarioNotFoundException(id));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    public void hardDeleteQuadraHorarioById(Long id) {

        this.quadraHorarioRepository.deleteById(id);
    }
}
