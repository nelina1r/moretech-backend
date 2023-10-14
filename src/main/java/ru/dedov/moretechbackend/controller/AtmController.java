package ru.dedov.moretechbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.moretechbackend.model.entity.Atm;
import ru.dedov.moretechbackend.service.interfaces.AtmService;

import java.io.IOException;
import java.util.List;

@RestController
public class AtmController {

    private final AtmService atmService;

    @Autowired
    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @Operation(summary = "поиск банкомата по Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "банкомат найден",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Atm.class))}),
            @ApiResponse(responseCode = "400", description = "банкомат не найден",
                    content = {@Content(mediaType = "application/json")})})
    @GetMapping("/atms/{id}")
    public ResponseEntity<?> findAtmById(@PathVariable Long id) {
        Atm atm = atmService.getAtmById(id);

        if (atm == null)
            return new ResponseEntity<>("atm with id " + id + "doesnt exist", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(atm, HttpStatus.OK);
    }

    @Operation(summary = "вывод всех банкоматов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "банкоматы найдены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Atm.class))}),
            @ApiResponse(responseCode = "400", description = "банкоматы не найдены",
                    content = {@Content(mediaType = "application/json")})})
    @GetMapping("/atms")
    public ResponseEntity<?> findAllAtms() {
        List<Atm> atms = atmService.findAllAtms();
        return new ResponseEntity<>(atms, HttpStatus.OK);
    }

    @Operation(summary = "заполнение бд из json (банкоматы)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "бд заполнена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "бд уже была заполнена",
                    content = {@Content(mediaType = "application/json")})})
    @PostMapping("/atms/fillDatabase")
    public ResponseEntity<?> fillDatabaseFromJson() throws IOException {
        if (atmService.parseAndSaveAtms("atms.json"))
            return new ResponseEntity<>("done", HttpStatus.OK);
        return new ResponseEntity<>("database was filled actually", HttpStatus.CONFLICT);
    }
}

