package com.example.finanzaspersonalesapp.repository;

import com.example.finanzaspersonalesapp.model.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {

    List<Presupuesto> findByUsuarioIdUsuario(Long idUsuario);

    // Consulta nativa: presupuestos que han sido excedidos ese mes
    // Retorna: [nombre_categoria, monto_limite, total_gastado]
    @Query(value = """
        SELECT c.nombre AS categoria,
               p.monto_limite,
               COALESCE(SUM(t.monto), 0) AS total_gastado
        FROM presupuesto p
        INNER JOIN categoria c ON p.id_categoria = c.id_categoria
        LEFT JOIN transaccion t ON t.id_categoria = p.id_categoria
            AND t.id_usuario = p.id_usuario
            AND t.tipo = 'GASTO'
            AND EXTRACT(MONTH FROM t.fecha) = p.mes
            AND EXTRACT(YEAR FROM t.fecha) = p.anio
        WHERE p.id_usuario = :idUsuario
          AND p.mes = :mes
          AND p.anio = :anio
        GROUP BY c.nombre, p.monto_limite
        HAVING COALESCE(SUM(t.monto), 0) > p.monto_limite
        """, nativeQuery = true)
    List<Object[]> findPresupuestosExcedidos(@Param("idUsuario") Long idUsuario,
                                              @Param("mes") int mes,
                                              @Param("anio") int anio);
}
