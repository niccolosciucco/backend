package niccolosciucco.backend.service;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Circuito;
import niccolosciucco.backend.entity.Evento;
import niccolosciucco.backend.exception.ResourceNotFoundException;
import niccolosciucco.backend.repository.CircuitoRepository;
import niccolosciucco.backend.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final CircuitoRepository circuitoRepository;

    public List<Evento> getAll() {
        return eventoRepository.findAll();
    }

    public Evento getById(UUID id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato: " + id));
    }

    public Evento create(Evento evento) {
        evento.setCircuito(resolveCircuito(evento));
        return eventoRepository.save(evento);
    }

    public Evento update(UUID id, Evento updated) {
        Evento existing = getById(id);
        existing.setName(updated.getName());
        existing.setCircuito(resolveCircuito(updated));
        existing.setDate(updated.getDate());
        existing.setStatus(updated.getStatus());
        return eventoRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!eventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento non trovato: " + id);
        }
        eventoRepository.deleteById(id);
    }

    private Circuito resolveCircuito(Evento evento) {
        if (evento.getCircuito() == null || evento.getCircuito().getId() == null) {
            throw new ResourceNotFoundException("Nessun circuito specificato per l'evento");
        }
        return circuitoRepository.findById(evento.getCircuito().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Circuito non trovato: " + evento.getCircuito().getId()));
    }
}
