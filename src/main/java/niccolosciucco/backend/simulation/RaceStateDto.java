package niccolosciucco.backend.simulation;

import java.util.List;
import java.util.Map;

public record RaceStateDto(
        String trackName,
        int currentLap,
        int totalLaps,
        List<DriverStateDto> drivers,
        List<Map<String, Object>> lapTimeHistory,
        double fastestLapTime,
        String fastestLapDriver,
        double topSpeed,
        boolean isFinished
) {
}