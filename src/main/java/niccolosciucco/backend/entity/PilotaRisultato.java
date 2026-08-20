package niccolosciucco.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "pilota_risultati")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PilotaRisultato {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risultato_gara_id", nullable = false)
    private RisultatoGara risultatoGara;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilota_id", nullable = false)
    private Pilota pilota;

    @Column(name = "race_position")
    private Integer position;

    @Column(name = "gap_seconds")
    private Double gapSeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RaceResultStatus status;

    @Column(name = "fastest_lap", nullable = false)
    @Builder.Default
    private boolean fastestLap = false;
}