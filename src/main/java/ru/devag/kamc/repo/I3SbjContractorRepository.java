package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3SbjContractor;

public interface I3SbjContractorRepository extends CrudRepository<I3SbjContractor, Long> {
   List<I3SbjContractor> findBySbbSbjBstId(Long sbbSbjBstId);
}