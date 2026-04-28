package com.example.finanzaspersonalesapp.repository;

import com.example.finanzaspersonalesapp.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByTipo(String tipo);

    // Consulta nativa: categorías que tienen transacciones registradas de un usuario
    @Query(value = """
        SELECT DISTINCT c.* FROM categoria c
        INNER JOIN transaccion t ON t.id_categoria = c.id_categoria
        WHERE t.id_usuario = :idUsuario
        """, nativeQuery = true)
    List<Categoria> findCategoriasUsadasPorUsuario(@Param("idUsuario") Long idUsuario);
}
