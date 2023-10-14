package ru.dedov.moretechbackend.service.implement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dedov.moretechbackend.model.entity.Atm;
import ru.dedov.moretechbackend.model.entity.PlaceLoad;
import ru.dedov.moretechbackend.model.repository.AtmRepository;
import ru.dedov.moretechbackend.service.interfaces.AtmService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtmServiceImpl implements AtmService {

    private final AtmRepository atmRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Autowired
    public AtmServiceImpl(AtmRepository atmRepository, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.atmRepository = atmRepository;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Atm> findAllAtms() {
        return atmRepository.findAll();
    }

    @Override
    public Atm getAtmById(Long id) {
        Optional<Atm> atm = atmRepository.findById(id);
        return atm.orElse(null);
    }


    @Transactional
    @Override
    public boolean parseAndSaveAtms(String jsonFileName) throws IOException {
        if (!atmRepository.findAll().isEmpty())
            return false;
        //
        Resource resource = resourceLoader.getResource("classpath:" + jsonFileName);
        List<Atm> atms = objectMapper.readValue(new File(resource.getURI()), new TypeReference<List<Atm>>() {
        });

        for (Atm atm : atms) {
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

            atm.setPlaceLoads(placeLoads);
        }

        atmRepository.saveAll(atms);
        return true;
    }
}
