package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3PersComponent;

public interface I3PersComponentRepository extends CrudRepository<I3PersComponent, Long> {
   List<I3PersComponent> findByPrsItn(String prsItn);
}