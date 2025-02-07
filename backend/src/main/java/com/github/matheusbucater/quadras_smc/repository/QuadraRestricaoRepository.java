package com.github.matheusbucater.quadras_smc.repository;


import com.github.matheusbucater.quadras_smc.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuadraRestricaoRepository extends JpaRepository<Usuario, Long> {
}
