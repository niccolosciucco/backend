package niccolosciucco.backend.controller;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.PilotaStandingDto;
import niccolosciucco.backend.dto.TeamStandingDto;
import niccolosciucco.backend.service.ClassificaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/classifica")
@RequiredArgsConstructor
public class ClassificaController {

    private final ClassificaService classificaService;

    @GetMapping("/piloti")
    public List<PilotaStandingDto> classificaPiloti() {
        return classificaService.classificaPiloti();
    }

    @GetMapping("/costruttori")
    public List<TeamStandingDto> classificaCostruttori() {
        return classificaService.classificaCostruttori();
    }
}