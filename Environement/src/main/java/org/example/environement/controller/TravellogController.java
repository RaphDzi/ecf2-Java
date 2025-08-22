package org.example.environement.controller;

import org.example.environement.dto.travellogs.TravellogDtoReceive;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.entity.Travellog;
import org.example.environement.repository.TravellogRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.entity.Observation;
import org.example.environement.repository.TravellogRepository.TravellogStatsProjection;
import org.example.environement.repository.TravellogRepository.TravellogByModeProjection;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/travel-logs")
public class TravellogController {

    private final TravellogRepository travellogRepository;
    private final ObservationRepository observationRepository;

    public TravellogController(TravellogRepository travellogRepository, ObservationRepository observationRepository) {
        this.travellogRepository = travellogRepository;
        this.observationRepository = observationRepository;
    }


    @PostMapping
    public ResponseEntity<TravellogDtoResponse> create(@RequestBody TravellogDtoReceive body) {
        // 1) Charger l'observation à partir de l'id passé dans le JSON
        Observation observation = observationRepository.findById(body.getObservationId())
                .orElseThrow(() -> new IllegalArgumentException("Observation introuvable: id=" + body.getObservationId()));

        // 2) Construire le Travellog et lier l'observation
        Travellog entity = body.dtoToEntity();
        entity.setObservation(observation);

        // 3) Sauvegarder et retourner le DTO
        Travellog saved = travellogRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.entityToDto());
    }


    @GetMapping
    public ResponseEntity<TravellogListResponse> list(@RequestParam(defaultValue = "10") int pageSize,
                                                      @RequestParam(defaultValue = "0") int pageNumber) {
        List<TravellogDtoResponse> items = travellogRepository
                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by("id").descending()))
                .getContent()
                .stream()
                .map(Travellog::entityToDto)
                .toList();

        DoubleSummaryStatistics stats = items.stream()
                .mapToDouble(TravellogDtoResponse::getEstimatedCo2Kg)
                .summaryStatistics();

        return ResponseEntity.ok(new TravellogListResponse(items, stats.getSum()));
    }


    @GetMapping("/stats/{idObservation}")
    public ResponseEntity<TravellogStatsResponse> statsByObservation(@PathVariable long idObservation) {
        // Lecture directe en BDD via agrégations
        TravellogStatsProjection totals = travellogRepository.aggregateStatsByObservation(idObservation);
        List<TravellogByModeProjection> perMode = travellogRepository.aggregateEmissionsByMode(idObservation);

        Map<String, Double> byMode = perMode.stream()
                .collect(Collectors.toMap(TravellogByModeProjection::getMode, TravellogByModeProjection::getEmissionsKg));

        return ResponseEntity.ok(new TravellogStatsResponse(
                totals == null ? 0.0 : totals.getTotalDistanceKm(),
                totals == null ? 0.0 : totals.getTotalEmissionsKg(),
                byMode
        ));
    }

    public record TravellogListResponse(List<TravellogDtoResponse> items, double totalCo2Kg) {}

    // Réponse conforme à ton besoin
    public record TravellogStatsResponse(double totalDistanceKm,
                                         double totalEmissionsKg,
                                         Map<String, Double> byMode) {}
}