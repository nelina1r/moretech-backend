package ru.dedov.moretechbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.moretechbackend.dto.LocationInfoDto;
import ru.dedov.moretechbackend.model.entity.Atm;
import ru.dedov.moretechbackend.model.entity.Place;
import ru.dedov.moretechbackend.service.interfaces.ToPlaceDistanceCalculationService;

@RestController
public class ToPlaceDistanceCalculationController {

    private final ToPlaceDistanceCalculationService calculationService;

    @Autowired
    public ToPlaceDistanceCalculationController(ToPlaceDistanceCalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @Operation(summary = "найти кратчайшее отделение или банкомат к пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "отделение найдено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Atm.class))})})
    @PostMapping("/findPlaces")
    public ResponseEntity<?> findNearestPlace(@RequestBody LocationInfoDto locationInfoDto) {
        Place nearestPlace = calculationService.chooseNearestPlaceBasedOnHaversine(locationInfoDto);
        return new ResponseEntity<>(nearestPlace, HttpStatus.OK);
    }
}
