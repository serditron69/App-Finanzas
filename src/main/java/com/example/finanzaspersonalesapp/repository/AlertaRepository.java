package com.example.finanzaspersonalesapp.repository;

import com.example.finanzaspersonalesapp.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByUsuarioIdUsuario(Long idUsuario);

    // Consulta nativa: alertas no leídas de un usuario
    @Query(value = "SELECT * FROM alerta WHERE id_usuario = :idUsuario AND leida = false ORDER BY fecha_alerta DESC",
           nativeQuery = true)
    List<Alerta> findAlertasNoLeidas(@Param("idUsuario") Long idUsuario);
}
