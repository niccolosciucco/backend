package niccolosciucco.backend.service;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Circuito;
import niccolosciucco.backend.exception.DuplicateResourceException;
import niccolosciucco.backend.exception.ResourceInUseException;
import niccolosciucco.backend.exception.ResourceNotFoundException;
import niccolosciucco.backend.repository.CircuitoRepository;
import niccolosciucco.backend.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CircuitoService {

    private final CircuitoRepository circuitoRepository;
    private final EventoRepository eventoRepository;

    public List<Circuito> getAll() {
        return circuitoRepository.findAll();
    }

    public Circuito getById(UUID id) {
        return circuitoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Circuito non trovato: " + id));
    }

    public Circuito create(Circuito circuito) {
        if (circuitoRepository.existsByNameIgnoreCase(circuito.getName())) {
            throw new DuplicateResourceException("Esiste già un circuito con nome: " + circuito.getName());
        }
        return circuitoRepository.save(circuito);
    }

    public Circuito update(UUID id, Circuito updated) {
        Circuito existing = getById(id);
        existing.setName(updated.getName());
        existing.setLocation(updated.getLocation());
        existing.setCountry(updated.getCountry());
        existing.setLengthKm(updated.getLengthKm());
        existing.setLaps(updated.getLaps());
        existing.setTurns(updated.getTurns());
        existing.setDrsZones(updated.getDrsZones());
        existing.setLapRecordTime(updated.getLapRecordTime());
        existing.setLapRecordDriver(updated.getLapRecordDriver());
        existing.setLapRecordYear(updated.getLapRecordYear());
        existing.setDescription(updated.getDescription());
        return circuitoRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!circuitoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Circuito non trovato: " + id);
        }
        if (eventoRepository.existsByCircuitoId(id)) {
            throw new ResourceInUseException("Impossibile eliminare: ci sono ancora eventi collegati a questo circuito. Riassegnali o eliminali prima.");
        }
        circuitoRepository.deleteById(id);
    }
}