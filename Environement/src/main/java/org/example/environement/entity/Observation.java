package org.example.environement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.observation.ObservationDtoResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "observation")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Observation {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "observer_name", nullable = false)
    private String observerName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "observation_date", nullable = false)
    private LocalDate observationDate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "specie_id")
    private Specie specie;

    @OneToMany(mappedBy = "observation", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Travellog> travellogs;


    public ObservationDtoResponse entityToDto (){
        return ObservationDtoResponse.builder()
                .id(this.getId())
                .observerName(this.getObserverName())
                .location(this.getLocation())
                .latitude(this.getLatitude())
                .longitude(this.getLongitude())
                .observationDate(this.getObservationDate())
                .comment(this.getComment())
                .specie(this.getSpecie() != null ? this.getSpecie().entityToDto() : null)
                .travellogs(this.getTravellogs() != null
                        ? this.getTravellogs().stream().map(Travellog::entityToDto).collect(Collectors.toList())
                        : List.of())
                .build();
    }
}
