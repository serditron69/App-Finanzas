package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Alerta;
import java.util.List;
import java.util.Optional;

public interface AlertaService {
    List<Alerta> findAll();
    Optional<Alerta> findById(Long id);
    List<Alerta> findByUsuario(Long idUsuario);
    List<Alerta> findNoLeidas(Long idUsuario);
    Alerta save(Alerta alerta);
    Alerta marcarLeida(Long id);
    void delete(Long id);
}
