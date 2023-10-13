package ru.dedov.moretechbackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dedov.moretechbackend.model.entity.Atm;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtmRepository extends JpaRepository<Atm, Long> {

    Optional<Atm> findById(Long id);

    List<Atm> findAll();
}
