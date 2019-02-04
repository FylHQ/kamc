package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3AprmComponent;

public interface I3AprmComponentRepository extends CrudRepository<I3AprmComponent, Long> {
   List<I3AprmComponent> findByApmCadastralInfo(String apmCadastralInfo);
}