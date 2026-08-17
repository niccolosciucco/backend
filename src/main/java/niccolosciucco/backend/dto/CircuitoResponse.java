package niccolosciucco.backend.dto;

import niccolosciucco.backend.entity.Circuito;

import java.util.UUID;

public record CircuitoResponse(
        UUID id, String name, String location, String country, Double lengthKm, Integer laps,
        Integer turns, Integer drsZones, String lapRecordTime, String lapRecordDriver, Integer lapRecordYear,
        String description
) {
    public static CircuitoResponse from(Circuito c) {
        return new CircuitoResponse(c.getId(), c.getName(), c.getLocation(), c.getCountry(), c.getLengthKm(), c.getLaps(),
                c.getTurns(), c.getDrsZones(), c.getLapRecordTime(), c.getLapRecordDriver(), c.getLapRecordYear(), c.getDescription());
    }
}