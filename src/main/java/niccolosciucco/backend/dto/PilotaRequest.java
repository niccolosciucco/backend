package niccolosciucco.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PilotaRequest(
        @NotBlank String name,
        @NotNull UUID teamId,
        @NotBlank String nationality,
        @NotNull Integer number
) {
}
