package ru.dedov.moretechbackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dedov.moretechbackend.model.entity.OpenHour;

@Repository
public interface OpenHourRepository extends JpaRepository<OpenHour, Long> {
}
