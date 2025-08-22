package org.example.environement.controller;

import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.service.TravellogsService;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/travellog")
public class TravellogController {

    private final TravellogsService travellogsService;

    public TravellogController(TravellogsService travellogsService) {
        this.travellogsService = travellogsService;
    }

    @GetMapping
    public ResponseEntity<List<TravellogDtoResponse>> getAllTravellogs (){
        return ResponseEntity.ok(travellogsService.get(10));
    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<TravellogDtoStat> getStatFromObseration (@PathVariable long id){
        return ResponseEntity.ok(travellogsService.getStat(id));
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<Map<String,TravellogDtoStat>> getTravelStatForUserOnLAstMonth (@PathVariable String name){
        return ResponseEntity.ok(travellogsService.getStatForUserLastMonth(name));
    }
}
