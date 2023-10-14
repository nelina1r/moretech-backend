package ru.dedov.moretechbackend.service.implement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dedov.moretechbackend.model.entity.Office;
import ru.dedov.moretechbackend.model.entity.PlaceLoad;
import ru.dedov.moretechbackend.model.repository.OfficeRepository;
import ru.dedov.moretechbackend.model.repository.OpenHourRepository;
import ru.dedov.moretechbackend.service.interfaces.OfficeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class OfficeServiceImpl implements OfficeService {

    private final ObjectMapper objectMapper;
    private final OfficeRepository officeRepository;
    private final OpenHourRepository openHourRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public OfficeServiceImpl(ObjectMapper objectMapper, OfficeRepository officeRepository, OpenHourRepository openHourRepository, ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.officeRepository = officeRepository;
        this.openHourRepository = openHourRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Office> findAllOffices(){
        return officeRepository.findAll();
    }

    @Override
    public Office findOfficeById(Long id) {
        Optional<Office> office = officeRepository.findById(id);
        return office.orElse(null);
    }

    @Transactional
    @Override
    public boolean parseAndSaveOffices(String jsonFileName) throws IOException {
        if (!officeRepository.findAll().isEmpty())
            return false;
        //
        Resource resource = resourceLoader.getResource("classpath:" + jsonFileName);
        InputStream inputStream = resource.getInputStream();
        String json = new BufferedReader(new InputStreamReader(inputStream)).
                lines().collect(Collectors.joining("\n"));

        List<Office> offices = objectMapper.readValue(json, new TypeReference<>() {
        });

        for (Office office : offices) {
            openHourRepository.saveAll(office.getOpenHours());
            openHourRepository.saveAll(office.getOpenHoursIndividual());
        }

        for (Office office : offices) {
            List<PlaceLoad> placeLoads = new ArrayList<>();
            for (int i = 0; i < 24; i += 4) {
                PlaceLoad placeLoad = new PlaceLoad();

                if (i >= 16 && i < 20) {
                    placeLoad.setHourOfDay(String.format("%02d:00", i));
                    placeLoad.setLoad(Math.random() * 2 + 8);
                } else if (i >= 0 && i < 12) {
                    placeLoad.setHourOfDay(String.format("%02d:00", i));
                    placeLoad.setLoad(Math.random() + 0);
                } else {
                    placeLoad.setHourOfDay(String.format("%02d:00", i));
                    placeLoad.setLoad(Math.random() * 1);
                }
                placeLoads.add(placeLoad);
            }

            office.setPlaceLoads(placeLoads);
        }

        officeRepository.saveAll(offices);
        return true;
    }
}
