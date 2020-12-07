package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3CertComponent;

public interface I3CertComponentRepository extends CrudRepository<I3CertComponent, Long> {
   List<I3CertComponent> findByCerOgrn(String cerOgrn);
}