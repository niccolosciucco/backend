package niccolosciucco.backend.dto;

import java.util.UUID;

public record PilotaStandingDto(UUID pilotaId, String pilotaName, String teamName, int points) {
}