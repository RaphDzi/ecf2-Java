package org.example.environement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.entity.enums.TravelMode;

@Entity
@Table(name = "travellog")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Travellog {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "observation_id", nullable = false)
    private Observation observation;

    @Column(name = "distance_km", nullable = false)
    private Double distanceKm;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false, length = 32)
    private TravelMode mode;

    @Column(name = "estimated_co2_kg", nullable = false)
    private Double estimatedCo2Kg;

    public TravellogDtoResponse entityToDto() {
        return TravellogDtoResponse.builder()
                .id(this.getId() == null ? 0L : this.getId())
                .distanceKm(this.getDistanceKm() == null ? 0.0 : this.getDistanceKm())
                .mode(this.getMode() == null ? null : this.getMode().toString())
                .estimatedCo2Kg(this.getEstimatedCo2Kg() == null ? 0.0 : this.getEstimatedCo2Kg())
                .build();
    }

    @PrePersist
    @PreUpdate
    private void ensureCo2Computed() {
        calculateCO2();
        if (this.estimatedCo2Kg == null) {
            this.estimatedCo2Kg = 0.0;
        }
    }

    public void calculateCO2() {
        if (distanceKm == null || mode == null) {
            this.estimatedCo2Kg = 0.0;
            return;
        }
        double factorKgPerKm = switch (mode) {
            case CAR -> 0.192;
            case TRAIN -> 0.041;
            case BUS -> 0.105;
            case PLANE -> 0.255;
            case BIKE, WALKING -> 0.0;
        };
        this.estimatedCo2Kg = distanceKm * factorKgPerKm;
    }

    public Long getId() {
        return id;
    }
// ... existing code ...
}