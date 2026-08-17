package niccolosciucco.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CircuitoRequest(
        @NotBlank String name,
        @NotBlank String location,
        @NotBlank String country,
        @NotNull Double lengthKm,
        @NotNull Integer laps,
        @NotNull Integer turns,
        @NotNull Integer drsZones,
        String lapRecordTime,
        String lapRecordDriver,
        Integer lapRecordYear,
        String description
) {
}