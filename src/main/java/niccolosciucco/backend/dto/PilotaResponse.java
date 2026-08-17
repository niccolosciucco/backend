package niccolosciucco.backend.dto;

import niccolosciucco.backend.entity.Pilota;

import java.util.UUID;

public record PilotaResponse(UUID id, String name, UUID teamId, String teamName, String nationality, Integer number) {
    public static PilotaResponse from(Pilota pilota) {
        return new PilotaResponse(
                pilota.getId(), pilota.getName(),
                pilota.getTeam().getId(), pilota.getTeam().getName(),
                pilota.getNationality(), pilota.getNumber()
        );
    }
}