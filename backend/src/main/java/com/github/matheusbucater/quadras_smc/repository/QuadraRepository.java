package com.github.matheusbucater.quadras_smc.repository;


import com.github.matheusbucater.quadras_smc.entity.Quadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuadraRepository extends JpaRepository<Quadra, Long> {

    @Query(value = "SELECT * FROM quadras WHERE deleted_at IS NULL", nativeQuery = true)
    List<Quadra> findAllActive();
}
