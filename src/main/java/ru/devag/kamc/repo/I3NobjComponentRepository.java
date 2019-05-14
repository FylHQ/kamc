package ru.devag.kamc.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3NobjComponent;

public interface I3NobjComponentRepository extends CrudRepository<I3NobjComponent, Long> {
   Optional<I3NobjComponent> findByNobActualNumber(String nobNumber);
}