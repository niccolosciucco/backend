package niccolosciucco.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.EventoRequest;
import niccolosciucco.backend.dto.EventoResponse;
import niccolosciucco.backend.entity.Circuito;
import niccolosciucco.backend.entity.Evento;
import niccolosciucco.backend.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public List<EventoResponse> getAll() {
        return eventoService.getAll().stream().map(EventoResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventoResponse getById(@PathVariable UUID id) {
        return EventoResponse.from(eventoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EventoResponse> create(@Valid @RequestBody EventoRequest request) {
        Evento evento = Evento.builder()
                .name(request.name())
                .circuito(Circuito.builder().id(request.circuitoId()).build())
                .date(request.date())
                .status(request.status())
                .build();
        Evento saved = eventoService.create(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(EventoResponse.from(saved));
    }

    @PutMapping("/{id}")
    public EventoResponse update(@PathVariable UUID id, @Valid @RequestBody EventoRequest request) {
        Evento evento = Evento.builder()
                .name(request.name())
                .circuito(Circuito.builder().id(request.circuitoId()).build())
                .date(request.date())
                .status(request.status())
                .build();
        return EventoResponse.from(eventoService.update(id, evento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}