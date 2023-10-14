package ru.dedov.moretechbackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dedov.moretechbackend.model.entity.PlaceLoad;

public interface PlaceLoadRepository extends JpaRepository<PlaceLoad, Long> {
}
