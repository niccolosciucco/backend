package niccolosciucco.backend.repository;

import niccolosciucco.backend.entity.RisultatoGara;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RisultatoGaraRepository extends JpaRepository<RisultatoGara, UUID> {
}