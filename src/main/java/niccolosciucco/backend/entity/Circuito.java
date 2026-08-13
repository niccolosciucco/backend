package niccolosciucco.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "circuiti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Circuito {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "length_km", nullable = false)
    private Double lengthKm;

    @Column(name = "laps", nullable = false)
    private Integer laps;

    @Column(name = "turns", nullable = false)
    private Integer turns;

    @Column(name = "drs_zones", nullable = false)
    private Integer drsZones;

    @Column(name = "lap_record_time")
    private String lapRecordTime;

    @Column(name = "lap_record_driver")
    private String lapRecordDriver;

    @Column(name = "lap_record_year")
    private Integer lapRecordYear;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
