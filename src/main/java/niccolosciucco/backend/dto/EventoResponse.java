package niccolosciucco.backend.dto;

import niccolosciucco.backend.entity.EventStatus;
import niccolosciucco.backend.entity.Evento;

import java.time.LocalDate;
import java.util.UUID;

public record EventoResponse(UUID id, String name, UUID circuitoId, String circuitoName, LocalDate date,
                             EventStatus status) {
    public static EventoResponse from(Evento evento) {
        return new EventoResponse(evento.getId(), evento.getName(), evento.getCircuito().getId(), evento.getCircuito().getName(), evento.getDate(), evento.getStatus());
    }
}