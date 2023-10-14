package ru.dedov.moretechbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.moretechbackend.model.entity.Office;
import ru.dedov.moretechbackend.service.OfficeService;

import java.io.IOException;
import java.util.List;

@RestController
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping("/offices/{id}")
    public ResponseEntity<?> findOfficeById(@PathVariable Long id) {
        Office office = officeService.findOfficeById(id);

        if (office == null)
            return new ResponseEntity<>("office with id " + id + "doesnt exist", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(office, HttpStatus.OK);
    }

    @GetMapping("/offices")
    public ResponseEntity<?> findAllOffices() {
        List<Office> offices = officeService.findAllOffices();
        return new ResponseEntity<>(offices, HttpStatus.OK);
    }

    @PostMapping("/offices/fillDatabase")
    public ResponseEntity<?> fillDatabaseFromJson() throws IOException {
        if (officeService.parseAndSaveOffices("atms.json"))
            return new ResponseEntity<>("done", HttpStatus.OK);
        return new ResponseEntity<>("database was filled actually", HttpStatus.CONFLICT);
    }
}
