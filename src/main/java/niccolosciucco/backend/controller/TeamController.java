package niccolosciucco.backend.controller;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Team;
import niccolosciucco.backend.repository.TeamRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamRepository teamRepository;

    @GetMapping
    public List<Team> getAll() {
        return teamRepository.findAll();
    }
}
