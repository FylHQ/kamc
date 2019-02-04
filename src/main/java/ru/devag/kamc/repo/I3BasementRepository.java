package ru.devag.kamc.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3Basement;

public interface I3BasementRepository extends CrudRepository<I3Basement, Long> {
   Optional<I3Basement> findByBstNumber(String val);
   
}