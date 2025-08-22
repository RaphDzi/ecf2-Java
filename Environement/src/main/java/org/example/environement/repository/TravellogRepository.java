package org.example.environement.repository;

import org.example.environement.entity.Travellog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravellogRepository extends JpaRepository<Travellog, Long> {
    List<Travellog> findTravellogByObservation_Id(long id);

    // Projection pour les stats globales
    interface TravellogStatsProjection {
        double getTotalDistanceKm();
        double getTotalEmissionsKg();
    }

    // Projection pour les stats par mode
    interface TravellogByModeProjection {
        String getMode();
        double getEmissionsKg();
    }

    @Query("""
           select coalesce(sum(t.distanceKm), 0) as totalDistanceKm,
                  coalesce(sum(t.estimatedCo2Kg), 0) as totalEmissionsKg
           from Travellog t
           where t.observation.id = :observationId
           """)
    TravellogStatsProjection aggregateStatsByObservation(@Param("observationId") long observationId);

    @Query("""
           select cast(t.mode as string) as mode,
                  coalesce(sum(t.estimatedCo2Kg), 0) as emissionsKg
           from Travellog t
           where t.observation.id = :observationId
           group by t.mode
           """)
    List<TravellogByModeProjection> aggregateEmissionsByMode(@Param("observationId") long observationId);
}
