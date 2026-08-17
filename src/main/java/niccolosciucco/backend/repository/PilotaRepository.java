package niccolosciucco.backend.repository;

import niccolosciucco.backend.entity.Pilota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PilotaRepository extends JpaRepository<Pilota, UUID> {
    List<Pilota> findByTeamId(UUID teamId);

    boolean existsByTeamId(UUID teamId);
}