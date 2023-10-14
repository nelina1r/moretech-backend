package ru.dedov.moretechbackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dedov.moretechbackend.model.entity.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
