package niccolosciucco.backend.service;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.PilotaStandingDto;
import niccolosciucco.backend.dto.TeamStandingDto;
import niccolosciucco.backend.entity.Pilota;
import niccolosciucco.backend.entity.PilotaRisultato;
import niccolosciucco.backend.entity.RaceResultStatus;
import niccolosciucco.backend.entity.Team;
import niccolosciucco.backend.repository.PilotaRisultatoRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassificaService {

    private static final Map<Integer, Integer> PUNTI_PER_POSIZIONE = Map.ofEntries(
            Map.entry(1, 25), Map.entry(2, 18), Map.entry(3, 15), Map.entry(4, 12), Map.entry(5, 10),
            Map.entry(6, 8), Map.entry(7, 6), Map.entry(8, 4), Map.entry(9, 2), Map.entry(10, 1)
    );

    private final PilotaRisultatoRepository pilotaRisultatoRepository;

    private int calcolaPunti(PilotaRisultato risultato) {
        if (risultato.getStatus() == RaceResultStatus.DNF || risultato.getPosition() == null) {
            return 0;
        }
        int punti = PUNTI_PER_POSIZIONE.getOrDefault(risultato.getPosition(), 0);
        if (risultato.isFastestLap() && risultato.getPosition() <= 10) {
            punti += 1;
        }
        return punti;
    }

    public List<PilotaStandingDto> classificaPiloti() {
        List<PilotaRisultato> tutti = pilotaRisultatoRepository.findAll();

        Map<UUID, Integer> puntiPerPilota = new LinkedHashMap<>();
        Map<UUID, Pilota> pilotiPerId = new LinkedHashMap<>();
        for (PilotaRisultato r : tutti) {
            UUID id = r.getPilota().getId();
            puntiPerPilota.merge(id, calcolaPunti(r), Integer::sum);
            pilotiPerId.putIfAbsent(id, r.getPilota());
        }

        return puntiPerPilota.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(e -> {
                    Pilota p = pilotiPerId.get(e.getKey());
                    return new PilotaStandingDto(p.getId(), p.getName(), p.getTeam().getName(), e.getValue());
                })
                .collect(Collectors.toList());
    }

    public List<TeamStandingDto> classificaCostruttori() {
        List<PilotaRisultato> tutti = pilotaRisultatoRepository.findAll();

        Map<UUID, Integer> puntiPerTeam = new LinkedHashMap<>();
        Map<UUID, Team> teamPerId = new LinkedHashMap<>();
        for (PilotaRisultato r : tutti) {
            Team team = r.getPilota().getTeam();
            puntiPerTeam.merge(team.getId(), calcolaPunti(r), Integer::sum);
            teamPerId.putIfAbsent(team.getId(), team);
        }

        return puntiPerTeam.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(e -> {
                    Team t = teamPerId.get(e.getKey());
                    return new TeamStandingDto(t.getId(), t.getName(), t.getColorHex(), e.getValue());
                })
                .collect(Collectors.toList());
    }
}