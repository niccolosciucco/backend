package niccolosciucco.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "risultati_gara")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RisultatoGara {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circuito_id", nullable = false)
    private Circuito circuito;

    @Column(name = "event_date", nullable = false)
    private LocalDate date;

    @Column(name = "laps", nullable = false)
    private Integer laps;

    @OneToMany(mappedBy = "risultatoGara", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PilotaRisultato> risultati = new ArrayList<>();
}