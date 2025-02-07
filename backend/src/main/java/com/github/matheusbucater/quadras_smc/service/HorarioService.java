package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.dto.HorarioResponseDTO;
import com.github.matheusbucater.quadras_smc.dto.HorarioRequestDTO;
import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.entity.QuadraHorario;
import com.github.matheusbucater.quadras_smc.exception.QuadraNotFoundException;
import com.github.matheusbucater.quadras_smc.repository.ProfessorHorarioRepository;
import com.github.matheusbucater.quadras_smc.repository.QuadraHorarioRepository;
import com.github.matheusbucater.quadras_smc.repository.QuadraRepository;
import com.github.matheusbucater.quadras_smc.repository.QuadraRestricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HorarioService {

    @Autowired
    private QuadraRepository quadraRepository;

    @Autowired
    private QuadraHorarioRepository quadraHorarioRepository;
    @Autowired
    private QuadraRestricaoRepository quadraRestricaoRepository;
    @Autowired
    private ProfessorHorarioRepository professorHorarioRepository;

    public List<QuadraHorario> getAllActiveQuadraHorarioByIdQuadra(Long idQuadra) {

        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new QuadraNotFoundException(idQuadra));

        List<QuadraHorario> horarios = this.quadraHorarioRepository.findAllActiveByQuadra(quadra);

        return horarios;
    }

    public HorarioResponseDTO addQuadraHorario(Long idQuadra, HorarioRequestDTO horario) {

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

    public HorarioResponseDTO deleteQuadraHorarioById(Long id) {

        return this.quadraHorarioRepository.findById(id)
                .map(horario -> {
                    horario.setDeletedAt(LocalDateTime.now());
                    return HorarioResponseDTO.from(this.quadraHorarioRepository.save(horario));
                })
                .orElseThrow(() -> new QuadraNotFoundException(id));
    }
}
