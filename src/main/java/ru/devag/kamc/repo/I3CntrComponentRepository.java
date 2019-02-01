package ru.devag.kamc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3CntrComponent;

public interface I3CntrComponentRepository extends CrudRepository<I3CntrComponent, Long> {
   Page<I3CntrComponent> findAll(Pageable pageable);   
}