package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Alerta;
import com.example.finanzaspersonalesapp.repository.AlertaRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertaServiceImpl implements AlertaService {
    private final AlertaRepository alertaRepository;

    @Override public List<Alerta> findAll() { return alertaRepository.findAll(); }
    @Override public Optional<Alerta> findById(Long id) { return alertaRepository.findById(id); }
    @Override public List<Alerta> findByUsuario(Long id) { return alertaRepository.findByUsuarioIdUsuario(id); }
    @Override public List<Alerta> findNoLeidas(Long id) { return alertaRepository.findAlertasNoLeidas(id); }
    @Override public Alerta save(Alerta a) { return alertaRepository.save(a); }

    @Override
    public Alerta marcarLeida(Long id) {
        return alertaRepository.findById(id).map(a -> {
            a.setLeida(true);
            return alertaRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Alerta no encontrada: " + id));
    }

    @Override public void delete(Long id) { alertaRepository.deleteById(id); }
}
