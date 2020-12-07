package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3CmpyComponent;

public interface I3CmpyComponentRepository extends CrudRepository<I3CmpyComponent, Long> {
   List<I3CmpyComponent> findByCmpInn(String cmpInn);
   List<I3CmpyComponent> findByCmpOgrn(String cmpOgrn);
}