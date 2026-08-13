package niccolosciucco.backend.repository;

import niccolosciucco.backend.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    boolean existsByNameIgnoreCase(String name);
}