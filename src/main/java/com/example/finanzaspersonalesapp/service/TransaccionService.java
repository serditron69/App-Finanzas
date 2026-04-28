package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Transaccion;
import java.util.List;
import java.util.Optional;

public interface TransaccionService {
    List<Transaccion> findAll();
    Optional<Transaccion> findById(Long id);
    Transaccion save(Transaccion transaccion);
    Transaccion update(Long id, Transaccion transaccion);
    void delete(Long id);
    List<Transaccion> findByUsuarioMesAnio(Long idUsuario, int mes, int anio);
    List<Object[]> findResumenMensual(Long idUsuario, int mes, int anio);
    List<Object[]> findBalanceSemanal(Long idUsuario);
    List<Object[]> findBalanceAnualPorMes(Long idUsuario, int anio);
    List<Object[]> findGastosPorCategoria(Long idUsuario, int mes, int anio);
    List<Object[]> findTop5CategoriasGastos(Long idUsuario);
    List<Object[]> findBalanceNetoPorMes(Long idUsuario, int anio);
}
