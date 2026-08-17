package niccolosciucco.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.CircuitoRequest;
import niccolosciucco.backend.dto.CircuitoResponse;
import niccolosciucco.backend.entity.Circuito;
import niccolosciucco.backend.service.CircuitoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/circuiti")
@RequiredArgsConstructor
public class CircuitoController {

    private final CircuitoService circuitoService;

    @GetMapping
    public List<CircuitoResponse> getAll() {
        return circuitoService.getAll().stream().map(CircuitoResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CircuitoResponse getById(@PathVariable UUID id) {
        return CircuitoResponse.from(circuitoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CircuitoResponse> create(@Valid @RequestBody CircuitoRequest request) {
        Circuito circuito = Circuito.builder()
                .name(request.name()).location(request.location()).country(request.country())
                .lengthKm(request.lengthKm()).laps(request.laps()).turns(request.turns()).drsZones(request.drsZones())
                .lapRecordTime(request.lapRecordTime()).lapRecordDriver(request.lapRecordDriver()).lapRecordYear(request.lapRecordYear())
                .description(request.description())
                .build();
        Circuito saved = circuitoService.create(circuito);
        return ResponseEntity.status(HttpStatus.CREATED).body(CircuitoResponse.from(saved));
    }

    @PutMapping("/{id}")
    public CircuitoResponse update(@PathVariable UUID id, @Valid @RequestBody CircuitoRequest request) {
        Circuito circuito = Circuito.builder()
                .name(request.name()).location(request.location()).country(request.country())
                .lengthKm(request.lengthKm()).laps(request.laps()).turns(request.turns()).drsZones(request.drsZones())
                .lapRecordTime(request.lapRecordTime()).lapRecordDriver(request.lapRecordDriver()).lapRecordYear(request.lapRecordYear())
                .description(request.description())
                .build();
        return CircuitoResponse.from(circuitoService.update(id, circuito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        circuitoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}