package niccolosciucco.backend.service;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.PilotaStandingDto;
import niccolosciucco.backend.dto.TeamStandingDto;
import niccolosciucco.backend.entity.Pilota;
import niccolosciucco.backend.entity.PilotaRisultato;
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

    private final PilotaRisultatoRepository pilotaRisultatoRepository;
    private final PilotaRepository pilotaRepository;
    private final TeamRepository teamRepository;

    public List<PilotaStandingDto> classificaPiloti() {
        Map<UUID, Integer> puntiPerPilota = new LinkedHashMap<>();
        Map<UUID, Pilota> pilotiPerId = new LinkedHashMap<>();

        for (Pilota p : pilotaRepository.findAll()) {
            puntiPerPilota.put(p.getId(), 0);
            pilotiPerId.put(p.getId(), p);
        }

        for (PilotaRisultato r : pilotaRisultatoRepository.findAll()) {
            puntiPerPilota.merge(r.getPilota().getId(), PuntiF1.calcola(r), Integer::sum);
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
            puntiPerTeam.merge(r.getPilota().getTeam().getId(), PuntiF1.calcola(r), Integer::sum);
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