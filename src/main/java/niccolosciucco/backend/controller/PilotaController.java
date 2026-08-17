package niccolosciucco.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.PilotaRequest;
import niccolosciucco.backend.dto.PilotaResponse;
import niccolosciucco.backend.entity.Pilota;
import niccolosciucco.backend.entity.Team;
import niccolosciucco.backend.service.PilotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/piloti")
@RequiredArgsConstructor
public class PilotaController {

    private final PilotaService pilotaService;

    @GetMapping
    public List<PilotaResponse> getAll() {
        return pilotaService.getAll().stream().map(PilotaResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PilotaResponse getById(@PathVariable UUID id) {
        return PilotaResponse.from(pilotaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PilotaResponse> create(@Valid @RequestBody PilotaRequest request) {
        Pilota pilota = Pilota.builder()
                .name(request.name())
                .team(Team.builder().id(request.teamId()).build())
                .nationality(request.nationality())
                .number(request.number())
                .build();
        Pilota saved = pilotaService.create(pilota);
        return ResponseEntity.status(HttpStatus.CREATED).body(PilotaResponse.from(saved));
    }

    @PutMapping("/{id}")
    public PilotaResponse update(@PathVariable UUID id, @Valid @RequestBody PilotaRequest request) {
        Pilota pilota = Pilota.builder()
                .name(request.name())
                .team(Team.builder().id(request.teamId()).build())
                .nationality(request.nationality())
                .number(request.number())
                .build();
        return PilotaResponse.from(pilotaService.update(id, pilota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        pilotaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}