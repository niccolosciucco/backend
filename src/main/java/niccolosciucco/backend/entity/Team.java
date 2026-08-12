package niccolosciucco.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "base", nullable = false)
    private String base;
    @Column(name = "principal", nullable = false)
    private String principal;
    @Column(name = "founded_year", nullable = false)
    private Integer foundedYear;
    @Column(name = "color_hex", nullable = false, length = 7)
    private String colorHex;
}