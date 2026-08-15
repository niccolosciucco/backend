package niccolosciucco.backend.controller;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Evento;
import niccolosciucco.backend.service.EventoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public List<Evento> getAll() {
        return eventoService.getAll();
    }
}
