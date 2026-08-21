package niccolosciucco.backend.dto;

import java.util.UUID;

public record TeamStandingDto(UUID teamId, String teamName, String colorHex, int points) {
}