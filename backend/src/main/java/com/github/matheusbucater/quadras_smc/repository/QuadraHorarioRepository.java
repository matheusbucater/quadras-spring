package com.github.matheusbucater.quadras_smc.repository;


import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.entity.QuadraHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuadraHorarioRepository extends JpaRepository<QuadraHorario, Long> {

    @Query(value = "SELECT * FROM quadra_horarios WHERE deleted_at IS NULL", nativeQuery = true)
    List<QuadraHorario> findAllActive();

    List<QuadraHorario> findAllActiveByQuadra(Quadra quadra);
}
