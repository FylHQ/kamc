package ru.devag.kamc.repo;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3ClassifierValue;

public interface I3ClassifierValueRepository extends CrudRepository<I3ClassifierValue, Long> {
   I3ClassifierValue findByCfvCode(String cfvCode);
}