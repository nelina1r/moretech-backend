package ru.dedov.moretechbackend.service.implement;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.dedov.moretechbackend.dto.LocationInfoDto;
import ru.dedov.moretechbackend.dto.PlacesSearchRequestDto;
import ru.dedov.moretechbackend.model.entity.Place;
import ru.dedov.moretechbackend.model.repository.PlaceRepository;
import ru.dedov.moretechbackend.service.interfaces.ToPlaceDistanceCalculationService;

import java.util.List;

@Service
@Log
public class ToPlaceDistanceCalculationServiceImpl implements ToPlaceDistanceCalculationService {

    @Value("${2gis.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    private final PlaceRepository placeRepository;

    @Autowired
    public ToPlaceDistanceCalculationServiceImpl(RestTemplate restTemplate, PlaceRepository placeRepository) {
        this.restTemplate = restTemplate;
        this.placeRepository = placeRepository;
    }

    @Override
    public Place chooseNearestPlaceBasedOnHaversine(LocationInfoDto infoDto) {
        List<Place> places = placeRepository.findAll();
        log.info(String.valueOf(places.size()));
        Place nearestPlace = null;
        double minDistance = Double.MAX_VALUE;

        for(Place place : places){
            double placeLat = place.getLatitude();
            double placeLon = place.getLongitude();
            double distance = calculateDistance(infoDto.getLatitude(), infoDto.getLongitude(), placeLat, placeLon);

            if (distance < minDistance) {
                minDistance = distance;
                nearestPlace = place;
            }
        }
        return nearestPlace;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public ResponseEntity<String> searchPlacesWithLocation(PlacesSearchRequestDto placesSearchRequestDto) {
        String url = apiUrl +
                (placesSearchRequestDto.getLocale() != null ? "?locale=" + placesSearchRequestDto.getLocale() : "?locale=ru_RU") +
                (placesSearchRequestDto.getQ() != null ? "&q=" + placesSearchRequestDto.getQ() : "") +
                (placesSearchRequestDto.getType() != null ? "&type=" + placesSearchRequestDto.getType() : "") +
                (placesSearchRequestDto.getLon() != null ? "&lon=" + placesSearchRequestDto.getLon() : "") +
                (placesSearchRequestDto.getLat() != null ? "&lat=" + placesSearchRequestDto.getLat() : "") +
                (placesSearchRequestDto.getFields() != null ? "&fields=" + placesSearchRequestDto.getFields() : "") +
                "&key=" ;

        log.info(url);
        return restTemplate.getForEntity(url, String.class);
    }
}
