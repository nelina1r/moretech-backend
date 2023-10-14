package ru.dedov.moretechbackend.service.interfaces;

import ru.dedov.moretechbackend.model.entity.Office;

import java.io.IOException;
import java.util.List;

public interface OfficeService {

    List<Office> findAllOffices();

    Office findOfficeById(Long id);

    boolean parseAndSaveOffices(String jsonFileName) throws IOException;
}
