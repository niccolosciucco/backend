package niccolosciucco.backend.controller;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.simulation.RaceSimulationService;
import niccolosciucco.backend.simulation.RaceStateDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/race")
@RequiredArgsConstructor
public class RaceController {

    private final RaceSimulationService raceSimulationService;

    @GetMapping("/state")
    public RaceStateDto getState() {
        return raceSimulationService.getState();
    }
}