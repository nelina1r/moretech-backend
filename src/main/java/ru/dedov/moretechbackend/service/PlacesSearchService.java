package ru.dedov.moretechbackend.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.dedov.moretechbackend.dto.PlacesSearchRequestDto;

@Service
@Log
public class PlacesSearchService {

    @Value("${2gis.api.url}")
    private String apiUrl;

    @Value("${2gis.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public PlacesSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> searchPlacesWithLocation(PlacesSearchRequestDto placesSearchRequestDto) {
        String url = apiUrl +
                (placesSearchRequestDto.getLocale() != null ? "?locale=" + placesSearchRequestDto.getLocale() : "?locale=ru_RU") +
                (placesSearchRequestDto.getQ() != null ? "&q=" + placesSearchRequestDto.getQ() : "") +
                (placesSearchRequestDto.getType() != null ? "&type=" + placesSearchRequestDto.getType() : "") +
                (placesSearchRequestDto.getLon() != null ? "&lon=" + placesSearchRequestDto.getLon() : "") +
                (placesSearchRequestDto.getLat() != null ? "&lat=" + placesSearchRequestDto.getLat() : "") +
                (placesSearchRequestDto.getFields() != null ? "&fields=" + placesSearchRequestDto.getFields() : "") +
                "&key=" + apiKey;

        log.info(url);
        return restTemplate.getForEntity(url, String.class);
    }
}
