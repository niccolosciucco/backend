package niccolosciucco.backend.dto;

import java.util.UUID;

public record PilotaStatsDto(
        UUID pilotaId,
        String pilotaName,
        String teamName,
        String nationality,
        Integer number,
        int points,
        int races,
        int wins,
        int podiums,
        int fastestLaps,
        int dnfs
) {
}