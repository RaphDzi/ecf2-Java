package org.example.environement.service;

import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.example.environement.entity.Travellog;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.repository.TravellogRepository; // <-- utiliser le vrai repository

@Service
public class TravellogsService {

    private final TravellogRepository travellogRepository;

    public TravellogsService(TravellogRepository travellogRepository) {
        this.travellogRepository = travellogRepository;
    }

    public List<TravellogDtoResponse> get(int pageNumber, int pageSize) {
        return travellogRepository.findAll(
                        PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(travellog -> new TravellogDtoResponse())
                .collect(Collectors.toList());
    }
    
    public List<TravellogDtoResponse> get(int observationId) {
        return travellogRepository.findTravellogByObservation_Id(observationId)
                .stream()
                .map(Travellog::entityToDto)
                .collect(Collectors.toList());
    }

    // Nouvelle méthode: getStat(long): TravellogDtoStat
    public TravellogDtoStat getStat(long observationId) {
        List<TravellogDtoResponse> items = travellogRepository.findTravellogByObservation_Id(observationId)
                .stream()
                .map(Travellog::entityToDto)
                .toList();

        double totalDistance = items.stream().mapToDouble(TravellogDtoResponse::getDistanceKm).sum();
        double totalCo2 = items.stream().mapToDouble(TravellogDtoResponse::getEstimatedCo2Kg).sum();

        TravellogDtoStat stat = new TravellogDtoStat();
        stat.addTotalDistanceKm(totalDistance);
        // Si TravellogDtoStat propose aussi un mutateur pour le CO2, ajoutez-le ici:
        // stat.addTotalCo2Kg(totalCo2);
        return stat;
    }
}