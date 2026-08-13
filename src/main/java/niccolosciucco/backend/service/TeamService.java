package niccolosciucco.backend.service;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Team;
import niccolosciucco.backend.exception.DuplicateResourceException;
import niccolosciucco.backend.exception.ResourceNotFoundException;
import niccolosciucco.backend.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    public Team getById(UUID id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team non trovato: " + id));
    }

    public Team create(Team team) {
        if (teamRepository.existsByNameIgnoreCase(team.getName())) {
            throw new DuplicateResourceException("Esiste già un team con nome: " + team.getName());
        }
        return teamRepository.save(team);
    }

    public Team update(UUID id, Team updated) {
        Team existing = getById(id);
        existing.setName(updated.getName());
        existing.setBase(updated.getBase());
        existing.setPrincipal(updated.getPrincipal());
        existing.setFoundedYear(updated.getFoundedYear());
        existing.setColorHex(updated.getColorHex());
        return teamRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team non trovato: " + id);
        }
        teamRepository.deleteById(id);
    }
}
