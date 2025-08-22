package org.example.environement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.specie.SpecieDtoResponse;
import org.example.environement.entity.enums.Category;

@Entity
@Table(name = "specie")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Specie {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "common_name", nullable = false)
    private String commonName;
    @Column(name = "scientific_name", nullable = false)
    private String scientificName;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    public SpecieDtoResponse entityToDto (){
        return SpecieDtoResponse.builder()
                .id(this.getId())
                .category(this.getCategory().toString())
                .scientificName(this.getScientificName())
                .commonName(this.getCommonName())
                .build();
    }

}
