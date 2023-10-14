package ru.dedov.moretechbackend.service.interfaces;

import ru.dedov.moretechbackend.model.entity.Atm;

import java.io.IOException;
import java.util.List;

public interface AtmService {

    boolean parseAndSaveAtms(String jsonFileName) throws IOException;

    Atm getAtmById(Long id);

    List<Atm> findAllAtms();
}
