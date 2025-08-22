package org.example.environement.repository;

import org.example.environement.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {

    List<Observation> findObservationByLocationIsLike(String location);

    List<Observation> findObservationBySpecieId(long specieId);

    List<Observation> findBySpecie_Id(long specieId);
}

