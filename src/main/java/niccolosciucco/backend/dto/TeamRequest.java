package niccolosciucco.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeamRequest(
        @NotBlank String name,
        @NotBlank String base,
        @NotBlank String principal,
        @NotNull Integer foundedYear,
        @NotBlank String colorHex
) {
}