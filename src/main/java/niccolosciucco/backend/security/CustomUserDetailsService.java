package niccolosciucco.backend.security;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Utente;
import niccolosciucco.backend.repository.UtenteRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + email));

        return User.builder()
                .username(utente.getEmail())
                .password(utente.getPasswordHash())
                .authorities("ROLE_" + utente.getRole().name())
                .build();
    }
}