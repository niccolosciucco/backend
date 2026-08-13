package niccolosciucco.backend.repository;

import niccolosciucco.backend.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {
    List<Evento> findByCircuitoId(UUID circuitoId);
}
