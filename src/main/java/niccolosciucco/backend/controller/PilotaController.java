package niccolosciucco.backend.controller;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Pilota;
import niccolosciucco.backend.service.PilotaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/piloti")
@RequiredArgsConstructor
public class PilotaController {

    private final PilotaService pilotaService;

    @GetMapping
    public List<Pilota> getAll() {
        return pilotaService.getAll();
    }
}
