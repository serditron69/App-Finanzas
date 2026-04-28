package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Presupuesto;
import java.util.List;
import java.util.Optional;

public interface PresupuestoService {
    List<Presupuesto> findAll();
    Optional<Presupuesto> findById(Long id);
    List<Presupuesto> findByUsuario(Long idUsuario);
    Presupuesto save(Presupuesto presupuesto);
    Presupuesto update(Long id, Presupuesto presupuesto);
    void delete(Long id);
    List<Object[]> findPresupuestosExcedidos(Long idUsuario, int mes, int anio);
}
