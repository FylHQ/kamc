package ru.devag.kamc.repo;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3Category;

public interface I3CategoryRepository extends CrudRepository<I3Category, Long> {
   I3Category findByCatCode(String catCode);
}