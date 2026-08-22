package niccolosciucco.backend.service;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.PilotaStatsDto;
import niccolosciucco.backend.entity.Pilota;
import niccolosciucco.backend.entity.PilotaRisultato;
import niccolosciucco.backend.entity.RaceResultStatus;
import niccolosciucco.backend.exception.ResourceNotFoundException;
import niccolosciucco.backend.repository.PilotaRepository;
import niccolosciucco.backend.repository.PilotaRisultatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PilotaStatsService {

    private final PilotaRepository pilotaRepository;
    private final PilotaRisultatoRepository pilotaRisultatoRepository;

    public PilotaStatsDto getStats(UUID pilotaId) {
        Pilota pilota = pilotaRepository.findById(pilotaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pilota non trovato: " + pilotaId));

        List<PilotaRisultato> risultati = pilotaRisultatoRepository.findByPilotaId(pilotaId);

        int points = risultati.stream().mapToInt(PuntiF1::calcola).sum();
        int races = risultati.size();
        int wins = (int) risultati.stream().filter(r -> r.getPosition() != null && r.getPosition() == 1).count();
        int podiums = (int) risultati.stream().filter(r -> r.getPosition() != null && r.getPosition() <= 3).count();
        int fastestLaps = (int) risultati.stream().filter(PilotaRisultato::isFastestLap).count();
        int dnfs = (int) risultati.stream().filter(r -> r.getStatus() == RaceResultStatus.DNF).count();

        return new PilotaStatsDto(
                pilota.getId(), pilota.getName(), pilota.getTeam().getName(), pilota.getNationality(), pilota.getNumber(),
                points, races, wins, podiums, fastestLaps, dnfs
        );
    }
}