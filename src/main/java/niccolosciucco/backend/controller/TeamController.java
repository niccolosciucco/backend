package niccolosciucco.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.TeamRequest;
import niccolosciucco.backend.dto.TeamResponse;
import niccolosciucco.backend.entity.Team;
import niccolosciucco.backend.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public List<TeamResponse> getAll() {
        return teamService.getAll().stream().map(TeamResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TeamResponse getById(@PathVariable UUID id) {
        return TeamResponse.from(teamService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TeamResponse> create(@Valid @RequestBody TeamRequest request) {
        Team team = Team.builder()
                .name(request.name())
                .base(request.base())
                .principal(request.principal())
                .foundedYear(request.foundedYear())
                .colorHex(request.colorHex())
                .build();
        Team saved = teamService.create(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(TeamResponse.from(saved));
    }

    @PutMapping("/{id}")
    public TeamResponse update(@PathVariable UUID id, @Valid @RequestBody TeamRequest request) {
        Team team = Team.builder()
                .name(request.name())
                .base(request.base())
                .principal(request.principal())
                .foundedYear(request.foundedYear())
                .colorHex(request.colorHex())
                .build();
        return TeamResponse.from(teamService.update(id, team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}