package niccolosciucco.backend.repository;

import niccolosciucco.backend.entity.Circuito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CircuitoRepository extends JpaRepository<Circuito, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
