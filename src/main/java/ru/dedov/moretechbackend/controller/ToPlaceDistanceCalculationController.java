package ru.dedov.moretechbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.moretechbackend.dto.LocationInfoDto;
import ru.dedov.moretechbackend.model.entity.Place;
import ru.dedov.moretechbackend.service.interfaces.ToPlaceDistanceCalculationService;

@RestController
public class ToPlaceDistanceCalculationController {

    private final ToPlaceDistanceCalculationService calculationService;

    @Autowired
    public ToPlaceDistanceCalculationController(ToPlaceDistanceCalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping("/findPlaces")
    public ResponseEntity<?> findNearestPlace(@RequestBody LocationInfoDto locationInfoDto) {
        Place nearestPlace = calculationService.chooseNearestPlaceBasedOnHaversine(locationInfoDto);
        return new ResponseEntity<>(nearestPlace, HttpStatus.NOT_FOUND);
    }
}
