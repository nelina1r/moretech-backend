package ru.dedov.moretechbackend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dedov.moretechbackend.model.entity.Office;
import ru.dedov.moretechbackend.model.repository.OfficeRepository;
import ru.dedov.moretechbackend.model.repository.OpenHourRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeService {

    private final ObjectMapper objectMapper;
    private final OfficeRepository officeRepository;
    private final OpenHourRepository openHourRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public OfficeService(ObjectMapper objectMapper, OfficeRepository officeRepository, OpenHourRepository openHourRepository, ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.officeRepository = officeRepository;
        this.openHourRepository = openHourRepository;
        this.resourceLoader = resourceLoader;
    }

    public List<Office> findAllOffices(){
        return officeRepository.findAll();
    }

    public Office findOfficeById(Long id) {
        Optional<Office> office = officeRepository.findById(id);
        return office.orElse(null);
    }

    @Transactional
    public void parseAndSaveOffices(String jsonFileName) throws IOException {
        if (officeRepository.countAll().compareTo(0L) > 0)
            throw new RuntimeException();
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

        officeRepository.saveAll(offices);
    }
}
