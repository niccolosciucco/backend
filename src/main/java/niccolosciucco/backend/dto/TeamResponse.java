package niccolosciucco.backend.dto;

import niccolosciucco.backend.entity.Team;

import java.util.UUID;

public record TeamResponse(UUID id, String name, String base, String principal, Integer foundedYear, String colorHex) {
    public static TeamResponse from(Team team) {
        return new TeamResponse(team.getId(), team.getName(), team.getBase(), team.getPrincipal(), team.getFoundedYear(), team.getColorHex());
    }
}