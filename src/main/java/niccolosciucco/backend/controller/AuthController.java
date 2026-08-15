package niccolosciucco.backend.controller;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.dto.LoginRequest;
import niccolosciucco.backend.dto.LoginResponse;
import niccolosciucco.backend.entity.Utente;
import niccolosciucco.backend.repository.UtenteRepository;
import niccolosciucco.backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtenteRepository utenteRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Credenziali non valide");
        }

        Utente utente = utenteRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Credenziali non valide"));

        String token = jwtService.generateToken(utente.getEmail(), utente.getRole().name());
        return new LoginResponse(token);
    }
}