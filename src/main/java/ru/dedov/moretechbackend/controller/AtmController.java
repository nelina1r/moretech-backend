package ru.dedov.moretechbackend.controller;

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

    @GetMapping("/atms/{id}")
    public ResponseEntity<?> findAtmById(@PathVariable Long id) {
        Atm atm = atmService.getAtmById(id);

        if (atm == null)
            return new ResponseEntity<>("atm with id " + id + "doesnt exist", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(atm, HttpStatus.OK);
    }

    @GetMapping("/atms")
    public ResponseEntity<?> findAllAtms() {
        List<Atm> atms = atmService.findAllAtms();
        return new ResponseEntity<>(atms, HttpStatus.OK);
    }

    @PostMapping("/atms/fillDatabase")
    public ResponseEntity<?> fillDatabaseFromJson() throws IOException {
        if (atmService.parseAndSaveAtms("atms.json"))
            return new ResponseEntity<>("done", HttpStatus.OK);
        return new ResponseEntity<>("database was filled actually", HttpStatus.CONFLICT);
    }
}

