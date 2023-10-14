package ru.dedov.moretechbackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dedov.moretechbackend.model.entity.Office;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    @Query(value = "select count(o.id) from Office o")
    Long countAll();

    Optional<Office> findById(Long id);

    List<Office> findAll();
}
