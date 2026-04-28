package com.example.finanzaspersonalesapp.repository;

import com.example.finanzaspersonalesapp.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    // ─── CONSULTAS NATIVAS ─────────────────────────────────────────────────────

    // 1. Transacciones de un usuario en un mes y año específico
    @Query(value = """
        SELECT * FROM transaccion
        WHERE id_usuario = :idUsuario
          AND EXTRACT(MONTH FROM fecha) = :mes
          AND EXTRACT(YEAR FROM fecha) = :anio
        ORDER BY fecha DESC
        """, nativeQuery = true)
    List<Transaccion> findByUsuarioMesAnio(@Param("idUsuario") Long idUsuario,
                                            @Param("mes") int mes,
                                            @Param("anio") int anio);

    // 2. Resumen mensual: total ingresos y total gastos de un usuario en un mes
    //    Retorna: [tipo, total]  — para gráfico de barras mensual
    @Query(value = """
        SELECT tipo, SUM(monto) AS total
        FROM transaccion
        WHERE id_usuario = :idUsuario
          AND EXTRACT(MONTH FROM fecha) = :mes
          AND EXTRACT(YEAR FROM fecha) = :anio
        GROUP BY tipo
        """, nativeQuery = true)
    List<Object[]> findResumenMensual(@Param("idUsuario") Long idUsuario,
                                      @Param("mes") int mes,
                                      @Param("anio") int anio);

    // 3. Balance semanal: ingresos y gastos de los últimos 7 días
    //    Retorna: [dia, tipo, total]  — para gráfico semanal día a día
    @Query(value = """
        SELECT TO_CHAR(fecha, 'Day') AS dia,
               tipo,
               SUM(monto) AS total
        FROM transaccion
        WHERE id_usuario = :idUsuario
          AND fecha >= CURRENT_DATE - INTERVAL '6 days'
        GROUP BY fecha, tipo
        ORDER BY fecha ASC
        """, nativeQuery = true)
    List<Object[]> findBalanceSemanal(@Param("idUsuario") Long idUsuario);

    // 4. Balance anual mes a mes: para gráfico de línea anual
    //    Retorna: [mes, tipo, total]
    @Query(value = """
        SELECT EXTRACT(MONTH FROM fecha) AS mes,
               tipo,
               SUM(monto) AS total
        FROM transaccion
        WHERE id_usuario = :idUsuario
          AND EXTRACT(YEAR FROM fecha) = :anio
        GROUP BY EXTRACT(MONTH FROM fecha), tipo
        ORDER BY mes ASC
        """, nativeQuery = true)
    List<Object[]> findBalanceAnualPorMes(@Param("idUsuario") Long idUsuario,
                                           @Param("anio") int anio);

    // 5. Gastos agrupados por categoría (para gráfico de torta/pie)
    //    Retorna: [nombre_categoria, total_gastado]
    @Query(value = """
        SELECT c.nombre AS categoria,
               SUM(t.monto) AS total
        FROM transaccion t
        INNER JOIN categoria c ON t.id_categoria = c.id_categoria
        WHERE t.id_usuario = :idUsuario
          AND t.tipo = 'GASTO'
          AND EXTRACT(MONTH FROM t.fecha) = :mes
          AND EXTRACT(YEAR FROM t.fecha) = :anio
        GROUP BY c.nombre
        ORDER BY total DESC
        """, nativeQuery = true)
    List<Object[]> findGastosPorCategoria(@Param("idUsuario") Long idUsuario,
                                           @Param("mes") int mes,
                                           @Param("anio") int anio);

    // 6. Top 5 categorías con más gastos históricos de un usuario
    @Query(value = """
        SELECT c.nombre AS categoria,
               SUM(t.monto) AS total
        FROM transaccion t
        INNER JOIN categoria c ON t.id_categoria = c.id_categoria
        WHERE t.id_usuario = :idUsuario AND t.tipo = 'GASTO'
        GROUP BY c.nombre
        ORDER BY total DESC
        LIMIT 5
        """, nativeQuery = true)
    List<Object[]> findTop5CategoriasGastos(@Param("idUsuario") Long idUsuario);

    // 7. Balance neto (ingresos - gastos) por mes del año
    @Query(value = """
        SELECT EXTRACT(MONTH FROM fecha) AS mes,
               SUM(CASE WHEN tipo = 'INGRESO' THEN monto ELSE -monto END) AS balance
        FROM transaccion
        WHERE id_usuario = :idUsuario
          AND EXTRACT(YEAR FROM fecha) = :anio
        GROUP BY EXTRACT(MONTH FROM fecha)
        ORDER BY mes ASC
        """, nativeQuery = true)
    List<Object[]> findBalanceNetoPorMes(@Param("idUsuario") Long idUsuario,
                                          @Param("anio") int anio);
}
