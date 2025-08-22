package org.example.environement;

import org.example.environement.entity.Observation;
import org.example.environement.entity.Specie;
import org.example.environement.entity.Travellog;
import org.example.environement.entity.enums.Category;
import org.example.environement.entity.enums.TravelMode;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.repository.SpecieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SpecieRepository specieRepository;
    private final ObservationRepository observationRepository;

    public DataInitializer(SpecieRepository specieRepository,
                           ObservationRepository observationRepository) {
        this.specieRepository = specieRepository;
        this.observationRepository = observationRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Seed uniquement si aucune observation n'existe pour éviter les doublons
        if (observationRepository.count() > 0) {
            return;
        }

        // 1) Espèces
        Specie robin = Specie.builder()
                .commonName("Rougegorge")
                .scientificName("Erithacus rubecula")
                .category(Category.BIRD)
                .build();

        Specie fox = Specie.builder()
                .commonName("Renard")
                .scientificName("Vulpes vulpes")
                .category(Category.MAMMAL)
                .build();

        robin = specieRepository.save(robin);
        fox = specieRepository.save(fox);

        // 2) Observations
        Observation obs1 = Observation.builder()
                .observerName("Alice")
                .location("Paris")
                .latitude(48.8566)
                .longitude(2.3522)
                .observationDate(LocalDate.now().minusDays(3))
                .comment("Observation matinale")
                .specie(robin)
                .build();

        Observation obs2 = Observation.builder()
                .observerName("Bob")
                .location("Lyon")
                .latitude(45.7640)
                .longitude(4.8357)
                .observationDate(LocalDate.now().minusDays(1))
                .comment("Observation en fin d'après-midi")
                .specie(fox)
                .build();

        // 3) Trajets (Travel logs) liés aux observations
        Travellog t1 = Travellog.builder()
                .observation(obs1)
                .distanceKm(12.5)
                .mode(TravelMode.CAR)
                .build();
        t1.calculateCO2();

        Travellog t2 = Travellog.builder()
                .observation(obs1)
                .distanceKm(3.2)
                .mode(TravelMode.WALKING)
                .build();
        t2.calculateCO2();

        Travellog t3 = Travellog.builder()
                .observation(obs2)
                .distanceKm(25.0)
                .mode(TravelMode.TRAIN)
                .build();
        t3.calculateCO2();

        obs1.setTravellogs(List.of(t1, t2));
        obs2.setTravellogs(List.of(t3));

        observationRepository.saveAll(List.of(obs1, obs2));
    }
}