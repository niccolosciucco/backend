package niccolosciucco.backend.controller;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Circuito;
import niccolosciucco.backend.service.CircuitoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/circuiti")
@RequiredArgsConstructor
public class CircuitoController {

    private final CircuitoService circuitoService;

    @GetMapping
    public List<Circuito> getAll() {
        return circuitoService.getAll();
    }
}
