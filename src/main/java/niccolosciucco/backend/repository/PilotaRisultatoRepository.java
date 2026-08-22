package niccolosciucco.backend.repository;

import niccolosciucco.backend.entity.PilotaRisultato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PilotaRisultatoRepository extends JpaRepository<PilotaRisultato, UUID> {
    List<PilotaRisultato> findByPilotaId(UUID pilotaId);
}