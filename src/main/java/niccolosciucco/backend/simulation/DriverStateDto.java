package niccolosciucco.backend.simulation;

public record DriverStateDto(
        String id,
        String code,
        String name,
        String team,
        int position,
        double gapSeconds,
        double lastLapSeconds,
        String tireCompound,
        double tireWearPercent,
        double fuelPercent,
        double ersPercent
) {
}