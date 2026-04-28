package com.example.finanzaspersonalesapp.repository;

import com.example.finanzaspersonalesapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    // Consulta nativa: buscar usuario por email y password (login básico)
    @Query(value = "SELECT * FROM usuario WHERE email = :email AND password = :password LIMIT 1",
           nativeQuery = true)
    Optional<Usuario> findByEmailAndPassword(@Param("email") String email,
                                              @Param("password") String password);
}
