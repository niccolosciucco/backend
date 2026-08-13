package niccolosciucco.backend.service;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Pilota;
import niccolosciucco.backend.entity.Team;
import niccolosciucco.backend.exception.ResourceNotFoundException;
import niccolosciucco.backend.repository.PilotaRepository;
import niccolosciucco.backend.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PilotaService {

    private final PilotaRepository pilotaRepository;
    private final TeamRepository teamRepository;

    public List<Pilota> getAll() {
        return pilotaRepository.findAll();
    }

    public List<Pilota> getByTeamId(UUID teamId) {
        return pilotaRepository.findByTeamId(teamId);
    }

    public Pilota getById(UUID id) {
        return pilotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pilota non trovato: " + id));
    }

    public Pilota create(Pilota pilota) {
        pilota.setTeam(resolveTeam(pilota));
        return pilotaRepository.save(pilota);
    }

    public Pilota update(UUID id, Pilota updated) {
        Pilota existing = getById(id);
        existing.setName(updated.getName());
        existing.setTeam(resolveTeam(updated));
        existing.setNationality(updated.getNationality());
        existing.setNumber(updated.getNumber());
        return pilotaRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!pilotaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pilota non trovato: " + id);
        }
        pilotaRepository.deleteById(id);
    }

    private Team resolveTeam(Pilota pilota) {
        if (pilota.getTeam() == null || pilota.getTeam().getId() == null) {
            throw new ResourceNotFoundException("Nessun team specificato per il pilota");
        }
        return teamRepository.findById(pilota.getTeam().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Team non trovato: " + pilota.getTeam().getId()));
    }
}
