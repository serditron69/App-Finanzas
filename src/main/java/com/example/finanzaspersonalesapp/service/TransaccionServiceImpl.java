package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Transaccion;
import com.example.finanzaspersonalesapp.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {
    private final TransaccionRepository transaccionRepository;

    @Override public List<Transaccion> findAll() { return transaccionRepository.findAll(); }
    @Override public Optional<Transaccion> findById(Long id) { return transaccionRepository.findById(id); }
    @Override public Transaccion save(Transaccion t) { return transaccionRepository.save(t); }

    @Override
    public Transaccion update(Long id, Transaccion t) {
        return transaccionRepository.findById(id).map(e -> {
            e.setTipo(t.getTipo());
            e.setMonto(t.getMonto());
            e.setDescripcion(t.getDescripcion());
            e.setFecha(t.getFecha());
            e.setCategoria(t.getCategoria());
            return transaccionRepository.save(e);
        }).orElseThrow(() -> new RuntimeException("Transacción no encontrada: " + id));
    }

    @Override public void delete(Long id) { transaccionRepository.deleteById(id); }
    @Override public List<Transaccion> findByUsuarioMesAnio(Long id, int mes, int anio) { return transaccionRepository.findByUsuarioMesAnio(id, mes, anio); }
    @Override public List<Object[]> findResumenMensual(Long id, int mes, int anio) { return transaccionRepository.findResumenMensual(id, mes, anio); }
    @Override public List<Object[]> findBalanceSemanal(Long id) { return transaccionRepository.findBalanceSemanal(id); }
    @Override public List<Object[]> findBalanceAnualPorMes(Long id, int anio) { return transaccionRepository.findBalanceAnualPorMes(id, anio); }
    @Override public List<Object[]> findGastosPorCategoria(Long id, int mes, int anio) { return transaccionRepository.findGastosPorCategoria(id, mes, anio); }
    @Override public List<Object[]> findTop5CategoriasGastos(Long id) { return transaccionRepository.findTop5CategoriasGastos(id); }
    @Override public List<Object[]> findBalanceNetoPorMes(Long id, int anio) { return transaccionRepository.findBalanceNetoPorMes(id, anio); }
}
