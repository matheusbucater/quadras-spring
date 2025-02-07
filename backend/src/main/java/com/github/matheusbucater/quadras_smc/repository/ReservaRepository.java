package com.github.matheusbucater.quadras_smc.repository;


import com.github.matheusbucater.quadras_smc.dto.ReservaResponseDTO;
import com.github.matheusbucater.quadras_smc.entity.Quadra;
import com.github.matheusbucater.quadras_smc.entity.Reserva;
import com.github.matheusbucater.quadras_smc.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query(value = "SELECT * FROM reservas WHERE deleted_at IS NULL", nativeQuery = true)
    List<Reserva> getAllActive();

    List<Reserva> getAllActiveByQuadra(Quadra quadra);
    List<Reserva> getAllActiveByDonoDaReserva(Usuario dono);
    List<Reserva> getAllActiveByJogadoresContains(Usuario jogador);
}
