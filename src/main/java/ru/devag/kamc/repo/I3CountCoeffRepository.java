package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3CountCoeff;

public interface I3CountCoeffRepository extends CrudRepository<I3CountCoeff, Long> {
   List<I3CountCoeff> findByCocModuleAndCocUse(String module, String isUse);
   
}