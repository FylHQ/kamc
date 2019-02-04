package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3NetwComponent;

public interface I3NetwComponentRepository extends CrudRepository<I3NetwComponent, Long> {
   List<I3NetwComponent> findByNetCadastralInfo(String netCadastralInfo);
}