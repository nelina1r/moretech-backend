package ru.dedov.moretechbackend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dedov.moretechbackend.model.entity.Atm;
import ru.dedov.moretechbackend.model.repository.AtmRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AtmService {

    private final AtmRepository atmRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Autowired
    public AtmService(AtmRepository atmRepository, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.atmRepository = atmRepository;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public List<Atm> findAllAtms(){
        return atmRepository.findAll();
    }

    public Atm getAtmById(Long id) {
        Optional<Atm> atm = atmRepository.findById(id);
        return atm.orElse(null);
    }

    @Transactional
    public void parseAndSaveAtms(String jsonFileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + jsonFileName);
        List<Atm> atms = objectMapper.readValue(new File(resource.getURI()), new TypeReference<List<Atm>>() {});

        atmRepository.saveAll(atms);
    }
}
