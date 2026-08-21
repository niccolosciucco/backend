package niccolosciucco.backend.service;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.PilotaStandingDto;
import niccolosciucco.backend.dto.TeamStandingDto;
import niccolosciucco.backend.entity.Pilota;
import niccolosciucco.backend.entity.PilotaRisultato;
import niccolosciucco.backend.entity.RaceResultStatus;
import niccolosciucco.backend.entity.Team;
import niccolosciucco.backend.repository.PilotaRepository;
import niccolosciucco.backend.repository.PilotaRisultatoRepository;
import niccolosciucco.backend.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassificaService {

    private static final Map<Integer, Integer> PUNTI_PER_POSIZIONE = Map.ofEntries(
            Map.entry(1, 25), Map.entry(2, 18), Map.entry(3, 15), Map.entry(4, 12), Map.entry(5, 10),
            Map.entry(6, 8), Map.entry(7, 6), Map.entry(8, 4), Map.entry(9, 2), Map.entry(10, 1)
    );

    private final PilotaRisultatoRepository pilotaRisultatoRepository;
    private final PilotaRepository pilotaRepository;
    private final TeamRepository teamRepository;

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
        Map<UUID, Integer> puntiPerPilota = new LinkedHashMap<>();
        Map<UUID, Pilota> pilotiPerId = new LinkedHashMap<>();

        // Si parte da TUTTI i piloti esistenti, a 0 punti: così chi non ha
        // ancora corso una gara nello storico resta comunque in classifica.
        for (Pilota p : pilotaRepository.findAll()) {
            puntiPerPilota.put(p.getId(), 0);
            pilotiPerId.put(p.getId(), p);
        }

        for (PilotaRisultato r : pilotaRisultatoRepository.findAll()) {
            puntiPerPilota.merge(r.getPilota().getId(), calcolaPunti(r), Integer::sum);
        }

        return puntiPerPilota.entrySet().stream()
                .map(e -> {
                    Pilota p = pilotiPerId.get(e.getKey());
                    return new PilotaStandingDto(p.getId(), p.getName(), p.getTeam().getName(), e.getValue());
                })
                .sorted(Comparator.comparingInt(PilotaStandingDto::points).reversed()
                        .thenComparing(PilotaStandingDto::pilotaName))
                .collect(Collectors.toList());
    }

    public List<TeamStandingDto> classificaCostruttori() {
        Map<UUID, Integer> puntiPerTeam = new LinkedHashMap<>();
        Map<UUID, Team> teamPerId = new LinkedHashMap<>();

        for (Team t : teamRepository.findAll()) {
            puntiPerTeam.put(t.getId(), 0);
            teamPerId.put(t.getId(), t);
        }

        for (PilotaRisultato r : pilotaRisultatoRepository.findAll()) {
            puntiPerTeam.merge(r.getPilota().getTeam().getId(), calcolaPunti(r), Integer::sum);
        }

        return puntiPerTeam.entrySet().stream()
                .map(e -> {
                    Team t = teamPerId.get(e.getKey());
                    return new TeamStandingDto(t.getId(), t.getName(), t.getColorHex(), e.getValue());
                })
                .sorted(Comparator.comparingInt(TeamStandingDto::points).reversed()
                        .thenComparing(TeamStandingDto::teamName))
                .collect(Collectors.toList());
    }
}