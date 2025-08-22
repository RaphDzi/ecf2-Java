package org.example.environement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.entity.enums.TravelMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Travellog {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "observation_id", nullable = false)
    private Observation observation;
    @Column(nullable = false)
    private Double distanceKm;
    @Column(nullable = false)
    private TravelMode mode;
    @Column(nullable = false)
    private Double estimatedCo2Kg;


    public TravellogDtoResponse entityToDto() {
        return TravellogDtoResponse.builder()
                .id(this.getId() == null ? 0L : this.getId())
                .distanceKm(this.getDistanceKm() == null ? 0.0 : this.getDistanceKm())
                .mode(this.getMode() == null ? null : this.getMode().toString())
                .estimatedCo2Kg(this.getEstimatedCo2Kg() == null ? 0.0 : this.getEstimatedCo2Kg())
                .build();
    }



    // Exemple simple de calcul suivant un facteur par mode
    public void calculateCO2() {
        if (distanceKm == null || mode == null) {
            this.estimatedCo2Kg = 0.0;
            return;
        }
        double factorKgPerKm = switch (mode) {
            case CAR -> 0.192;    // exemple
            case TRAIN -> 0.041;  // exemple
            case BUS -> 0.105;    // exemple
            case PLANE -> 0.255;  // exemple
            case BIKE, WALKING -> 0.0;
        };
        this.estimatedCo2Kg = distanceKm * factorKgPerKm;
    }

    public Long getId() {
        return id;
    }
}