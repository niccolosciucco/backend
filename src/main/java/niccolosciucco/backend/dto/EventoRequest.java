package niccolosciucco.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import niccolosciucco.backend.entity.EventStatus;

import java.time.LocalDate;
import java.util.UUID;

public record EventoRequest(
        @NotBlank String name,
        @NotNull UUID circuitoId,
        @NotNull LocalDate date,
        @NotNull EventStatus status
) {
}