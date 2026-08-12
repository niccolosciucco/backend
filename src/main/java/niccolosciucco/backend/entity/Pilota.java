package niccolosciucco.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "piloti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pilota {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    @Column(name = "nationality", nullable = false, length = 3)
    private String nationality;
    @Column(name = "number", nullable = false)
    private Integer number;
}