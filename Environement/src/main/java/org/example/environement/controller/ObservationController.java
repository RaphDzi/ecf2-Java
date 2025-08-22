package org.example.environement.controller;

import org.example.environement.dto.observation.ObservationDtoReceive;
import org.example.environement.dto.observation.ObservationDtoResponse;
import org.example.environement.service.ObservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

// ... existing code ...
@RestController
@RequestMapping("/observations")
public class ObservationController {

    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @PostMapping
    public ResponseEntity<ObservationDtoResponse> create(@Valid @RequestBody ObservationDtoReceive body) {
        ObservationDtoResponse created = observationService.create(body);
        return ResponseEntity.created(URI.create("/observations/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ObservationDtoResponse>> getAll(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber
    ) {
        return ResponseEntity.ok(observationService.get(pageSize, pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservationDtoResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(observationService.get(id));
    }

    @GetMapping("/by-location")
    public ResponseEntity<List<ObservationDtoResponse>> getObservationByLocation(@RequestParam String location) {
        return ResponseEntity.ok(observationService.getByLocation(location));
    }

    @GetMapping("/by-species/{speciesId}")
    public ResponseEntity<List<ObservationDtoResponse>> getObservationBySpecie(@PathVariable("speciesId") long speciesId) {
        return ResponseEntity.ok(observationService.getBySpecie(speciesId));
    }
}