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
import ru.dedov.moretechbackend.model.entity.Office;
import ru.dedov.moretechbackend.service.interfaces.OfficeService;

import java.io.IOException;
import java.util.List;

@RestController
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @Operation(summary = "поиск офиса по Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "офис найден",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Office.class))}),
            @ApiResponse(responseCode = "400", description = "офис не найден",
                    content = {@Content(mediaType = "application/json")})})
    @GetMapping("/offices/{id}")
    public ResponseEntity<?> findOfficeById(@PathVariable Long id) {
        Office office = officeService.findOfficeById(id);

        if (office == null)
            return new ResponseEntity<>("office with id " + id + "doesnt exist", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(office, HttpStatus.OK);
    }

    @Operation(summary = "вывод всех офисов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "офисы найдены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Office.class))}),
            @ApiResponse(responseCode = "400", description = "офисы не найдены",
                    content = {@Content(mediaType = "application/json")})})
    @GetMapping("/offices")
    public ResponseEntity<?> findAllOffices() {
        List<Office> offices = officeService.findAllOffices();
        return new ResponseEntity<>(offices, HttpStatus.OK);
    }

    @Operation(summary = "заполнение бд из json (офисы)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "бд заполнена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "бд уже была заполнена",
                    content = {@Content(mediaType = "application/json")})})
    @PostMapping("/offices/fillDatabase")
    public ResponseEntity<?> fillDatabaseFromJson() throws IOException {
        if (officeService.parseAndSaveOffices("offices.json"))
            return new ResponseEntity<>("done", HttpStatus.OK);
        return new ResponseEntity<>("database was filled actually", HttpStatus.CONFLICT);
    }
}
