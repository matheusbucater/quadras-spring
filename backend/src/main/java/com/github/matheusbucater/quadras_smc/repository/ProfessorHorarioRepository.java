package com.github.matheusbucater.quadras_smc.repository;


import com.github.matheusbucater.quadras_smc.entity.ProfessorHorario;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessorHorarioRepository extends JpaRepository<ProfessorHorario, Long> {

    @Query(value = "SELECT * FROM professor_horarios WHERE deleted_at IS NULL", nativeQuery = true)
    List<ProfessorHorario> findAllActive();

    List<ProfessorHorario> findAllActiveByProfessor(Usuario professor);
}
