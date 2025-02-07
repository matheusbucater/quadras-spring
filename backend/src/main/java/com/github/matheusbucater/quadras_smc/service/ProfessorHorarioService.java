package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.dto.HorarioRequestDTO;
import com.github.matheusbucater.quadras_smc.dto.HorarioResponseDTO;
import com.github.matheusbucater.quadras_smc.entity.ProfessorHorario;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import com.github.matheusbucater.quadras_smc.enums.TipoDeUsuario;
import com.github.matheusbucater.quadras_smc.exception.ProfessorHorarioNotFoundException;
import com.github.matheusbucater.quadras_smc.exception.UsuarioIsNotProfessorException;
import com.github.matheusbucater.quadras_smc.exception.UsuarioNotFoundException;
import com.github.matheusbucater.quadras_smc.repository.ProfessorHorarioRepository;
import com.github.matheusbucater.quadras_smc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfessorHorarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProfessorHorarioRepository professorHorarioRepository;

    public List<ProfessorHorario> getAllActiveHorarioByIdProfessor(Long idProfessor) {

        Usuario professor = usuarioRepository.findById(idProfessor)
                .orElseThrow(() -> new UsuarioNotFoundException(idProfessor));

        if (professor.getTipoDeUsuario() != TipoDeUsuario.PROFESSOR) {
            throw new UsuarioIsNotProfessorException(idProfessor);
        }

        return this.professorHorarioRepository.findAllActiveByProfessor(professor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public HorarioResponseDTO createHorario(Long idProfessor, HorarioRequestDTO data) {

        Usuario professor = usuarioRepository.findById(idProfessor)
                .orElseThrow(() -> new UsuarioNotFoundException(idProfessor));

        if (professor.getTipoDeUsuario() != TipoDeUsuario.PROFESSOR) {
            throw new UsuarioIsNotProfessorException(idProfessor);
        }

        ProfessorHorario horario = new ProfessorHorario(
                professor,
                data.dia_da_semana(),
                data.hr_inicio(),
                data.hr_fim()
        );

        return HorarioResponseDTO.from(this.professorHorarioRepository.save(horario));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public HorarioResponseDTO updateHorarioById(Long id, HorarioRequestDTO data) {

        return this.professorHorarioRepository.findById(id)
                .map(horario -> {
                    horario.setDiaDaSemana(
                            data.dia_da_semana() != null ? data.dia_da_semana() : horario.getDiaDaSemana()
                    );
                    horario.setHrInicio(
                            data.hr_inicio() != null ? data.hr_inicio() : horario.getHrInicio()
                    );
                    horario.setHrFim(
                            data.hr_fim() != null ? data.hr_fim() : horario.getHrFim()
                    );
                    return HorarioResponseDTO.from(this.professorHorarioRepository.save(horario));
                })
                .orElseThrow(() -> new ProfessorHorarioNotFoundException(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public HorarioResponseDTO deleteHorarioById(Long id) {

        return this.professorHorarioRepository.findById(id)
                .map(horario -> {
                    horario.setDeletedAt(LocalDateTime.now());
                    return HorarioResponseDTO.from(this.professorHorarioRepository.save(horario));
                })
                .orElseThrow(() -> new ProfessorHorarioNotFoundException(id));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    public void hardDeleteHorarioById(Long id) {

        this.professorHorarioRepository.deleteById(id);
    }
}
