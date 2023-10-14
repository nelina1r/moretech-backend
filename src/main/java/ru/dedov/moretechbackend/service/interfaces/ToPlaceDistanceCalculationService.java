package ru.dedov.moretechbackend.service.interfaces;

import ru.dedov.moretechbackend.dto.LocationInfoDto;
import ru.dedov.moretechbackend.model.entity.Place;

public interface ToPlaceDistanceCalculationService {

    Place chooseNearestPlaceBasedOnHaversine(LocationInfoDto infoDto);
}
