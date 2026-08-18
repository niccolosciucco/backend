package niccolosciucco.backend.simulation;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class RaceSimulationService {

    private static final int TOTAL_LAPS = 58;
    private static final int HISTORY_LENGTH = 12;

    private final SimpMessagingTemplate messagingTemplate;

    private RaceStateDto state = buildInitialState();

    private static double randomDelta(double range) {
        return (ThreadLocalRandom.current().nextDouble() - 0.5) * 2 * range;
    }

    private static double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    private static RaceStateDto buildInitialState() {
        List<DriverStateDto> drivers = List.of(
                new DriverStateDto("lec", "LEC", "Charles Leclerc", "Ferrari", 1, 0, 80.512, "soft", 5, 98, 92),
                new DriverStateDto("ham", "HAM", "Lewis Hamilton", "Ferrari", 2, 0.812, 80.601, "soft", 6, 98, 89),
                new DriverStateDto("rus", "RUS", "George Russell", "Mercedes", 3, 2.145, 80.833, "medium", 3, 100, 90),
                new DriverStateDto("ant", "ANT", "Kimi Antonelli", "Mercedes", 4, 3.29, 80.955, "medium", 3, 100, 88),
                new DriverStateDto("pia", "PIA", "Oscar Piastri", "McLaren", 5, 4.508, 81.077, "soft", 4, 99, 95),
                new DriverStateDto("nor", "NOR", "Lando Norris", "McLaren", 6, 5.734, 81.194, "soft", 5, 99, 93),
                new DriverStateDto("ver", "VER", "Max Verstappen", "Red Bull Racing", 7, 6.981, 81.328, "medium", 4, 99, 94),
                new DriverStateDto("had", "HAD", "Isack Hadjar", "Red Bull Racing", 8, 8.24, 81.455, "medium", 4, 99, 87),
                new DriverStateDto("alo", "ALO", "Fernando Alonso", "Aston Martin", 9, 9.602, 81.601, "hard", 2, 100, 85),
                new DriverStateDto("str", "STR", "Lance Stroll", "Aston Martin", 10, 10.93, 81.734, "hard", 2, 100, 83),
                new DriverStateDto("sai", "SAI", "Carlos Sainz", "Williams", 11, 12.301, 81.877, "medium", 5, 98, 91),
                new DriverStateDto("alb", "ALB", "Alex Albon", "Williams", 12, 13.689, 82.012, "medium", 5, 98, 88),
                new DriverStateDto("gas", "GAS", "Pierre Gasly", "Alpine", 13, 15.104, 82.166, "soft", 6, 97, 90),
                new DriverStateDto("col", "COL", "Franco Colapinto", "Alpine", 14, 16.522, 82.301, "soft", 7, 97, 86),
                new DriverStateDto("oco", "OCO", "Esteban Ocon", "Haas", 15, 17.98, 82.455, "hard", 3, 99, 84),
                new DriverStateDto("bea", "BEA", "Oliver Bearman", "Haas", 16, 19.441, 82.598, "hard", 3, 99, 82),
                new DriverStateDto("law", "LAW", "Liam Lawson", "Racing Bulls", 17, 20.933, 82.744, "medium", 5, 98, 89),
                new DriverStateDto("lin", "LIN", "Arvid Lindblad", "Racing Bulls", 18, 22.41, 82.891, "medium", 6, 98, 85),
                new DriverStateDto("hul", "HUL", "Nico Hülkenberg", "Audi", 19, 23.94, 83.045, "hard", 2, 100, 80),
                new DriverStateDto("bor", "BOR", "Gabriel Bortoleto", "Audi", 20, 25.475, 83.19, "hard", 2, 100, 78),
                new DriverStateDto("per", "PER", "Sergio Perez", "Cadillac", 21, 27.033, 83.348, "medium", 4, 99, 83),
                new DriverStateDto("bot", "BOT", "Valtteri Bottas", "Cadillac", 22, 28.601, 83.502, "medium", 4, 99, 81)
        );

        Map<String, Object> sample = new LinkedHashMap<>();
        sample.put("lap", 1);
        for (DriverStateDto d : drivers) {
            sample.put(d.code(), d.lastLapSeconds());
        }

        double fastest = drivers.stream().mapToDouble(DriverStateDto::lastLapSeconds).min().orElseThrow();

        return new RaceStateDto("Gp Monza, Autodromo Nazionale", 1, TOTAL_LAPS, drivers,
                new ArrayList<>(List.of(sample)), fastest, drivers.get(0).name(), 341, false);
    }

    public RaceStateDto getState() {
        return state;
    }

    @Scheduled(fixedRate = 4000)
    public void tick() {
        if (state.isFinished()) {
            return;
        }
        state = advance(state);
        messagingTemplate.convertAndSend("/topic/race", state);
        System.out.println("Tick gara: giro " + state.currentLap() + "/" + state.totalLaps());
    }

    private RaceStateDto advance(RaceStateDto current) {
        int nextLap = current.currentLap() + 1;

        List<DriverStateDto> updated = new ArrayList<>();
        for (DriverStateDto d : current.drivers()) {
            double lastLapSeconds = clamp(d.lastLapSeconds() + randomDelta(0.35), 78, 86);
            double gapSeconds = d.position() == 1 ? 0 : clamp(d.gapSeconds() + randomDelta(0.4), 0.2, 95);
            double tireWearPercent = clamp(d.tireWearPercent() + ThreadLocalRandom.current().nextDouble() * 2.5, 0, 100);
            double fuelPercent = clamp(d.fuelPercent() - ThreadLocalRandom.current().nextDouble() * 1.5, 0, 100);
            double ersPercent = clamp(d.ersPercent() + randomDelta(12), 20, 95);
            updated.add(new DriverStateDto(d.id(), d.code(), d.name(), d.team(), d.position(),
                    gapSeconds, lastLapSeconds, d.tireCompound(), tireWearPercent, fuelPercent, ersPercent));
        }

        updated.sort((a, b) -> Double.compare(a.gapSeconds(), b.gapSeconds()));
        List<DriverStateDto> withPositions = new ArrayList<>();
        for (int i = 0; i < updated.size(); i++) {
            DriverStateDto d = updated.get(i);
            withPositions.add(new DriverStateDto(d.id(), d.code(), d.name(), d.team(), i + 1,
                    d.gapSeconds(), d.lastLapSeconds(), d.tireCompound(), d.tireWearPercent(), d.fuelPercent(), d.ersPercent()));
        }

        Map<String, Object> sample = new LinkedHashMap<>();
        sample.put("lap", nextLap);
        for (DriverStateDto d : withPositions) {
            sample.put(d.code(), Math.round(d.lastLapSeconds() * 1000.0) / 1000.0);
        }
        List<Map<String, Object>> history = new ArrayList<>(current.lapTimeHistory());
        history.add(sample);
        if (history.size() > HISTORY_LENGTH) {
            history = history.subList(history.size() - HISTORY_LENGTH, history.size());
        }

        DriverStateDto bestThisTick = withPositions.stream()
                .min((a, b) -> Double.compare(a.lastLapSeconds(), b.lastLapSeconds()))
                .orElseThrow();
        boolean isNewRecord = bestThisTick.lastLapSeconds() < current.fastestLapTime();

        return new RaceStateDto(
                current.trackName(),
                nextLap,
                current.totalLaps(),
                withPositions,
                history,
                isNewRecord ? bestThisTick.lastLapSeconds() : current.fastestLapTime(),
                isNewRecord ? bestThisTick.name() : current.fastestLapDriver(),
                clamp(current.topSpeed() + randomDelta(4), 320, 350),
                nextLap >= current.totalLaps()
        );
    }
}