package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Presupuesto;
import com.example.finanzaspersonalesapp.repository.PresupuestoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresupuestoServiceImpl implements PresupuestoService {
    private final PresupuestoRepository presupuestoRepository;

    @Override public List<Presupuesto> findAll() { return presupuestoRepository.findAll(); }
    @Override public Optional<Presupuesto> findById(Long id) { return presupuestoRepository.findById(id); }
    @Override public List<Presupuesto> findByUsuario(Long id) { return presupuestoRepository.findByUsuarioIdUsuario(id); }
    @Override public Presupuesto save(Presupuesto p) { return presupuestoRepository.save(p); }

    @Override
    public Presupuesto update(Long id, Presupuesto p) {
        return presupuestoRepository.findById(id).map(e -> {
            e.setMontoLimite(p.getMontoLimite());
            e.setMes(p.getMes());
            e.setAnio(p.getAnio());
            e.setCategoria(p.getCategoria());
            return presupuestoRepository.save(e);
        }).orElseThrow(() -> new RuntimeException("Presupuesto no encontrado: " + id));
    }

    @Override public void delete(Long id) { presupuestoRepository.deleteById(id); }
    @Override public List<Object[]> findPresupuestosExcedidos(Long id, int mes, int anio) {
        return presupuestoRepository.findPresupuestosExcedidos(id, mes, anio);
    }
}
