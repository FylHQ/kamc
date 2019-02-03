package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3Object;

public interface I3ObjectRepository extends CrudRepository<I3Object, Long> {
   I3Object findByObjNumber(String val);
   List<I3Object> findByObjDescriptionIgnoreCase(String objDescription);
}