package com.github.matheusbucater.quadras_smc.repository;


import com.github.matheusbucater.quadras_smc.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Usuario findByEmail(String email);
    Usuario findByTelefone(String telefone);
    Usuario findByPasswordResetToken(String token);

    @Query(value = "SELECT * FROM usuarios WHERE deleted_at IS NULL", nativeQuery = true)
    List<Usuario> findAllActive();

    @Query(value = "SELECT * FROM usuarios WHERE tipo_de_usuario = :tipoDeUsuario", nativeQuery = true)
    List<Usuario> findAllActiveByTipoDeUsuario(@Param("tipoDeUsuario") String tipoDeUsuario);

}
