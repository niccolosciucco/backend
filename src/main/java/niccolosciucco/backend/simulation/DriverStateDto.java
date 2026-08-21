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
        double ersPercent,
        boolean pittedThisLap,
        int pitStopCount
) {
    public static DriverStateDto initial(String id, String code, String name, String team, int position,
                                         double gapSeconds, double lastLapSeconds, String tireCompound,
                                         double tireWearPercent, double fuelPercent, double ersPercent) {
        return new DriverStateDto(id, code, name, team, position, gapSeconds, lastLapSeconds,
                tireCompound, tireWearPercent, fuelPercent, ersPercent, false, 0);
    }
}