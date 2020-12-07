package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.devag.kamc.model.I3PaymComponent;

public interface I3PaymComponentRepository extends CrudRepository<I3PaymComponent, Long> {
   @Query("select paym" +
   " from I3PaymComponent paym" + 
   " join I3SbjBst sbb on paym.bst = sbb.bstBasementId" + 
   " where sbb.sbbType = 4109 and sbb.sbjSubjectId = :sbjId")
   List<I3PaymComponent> findBySbjId(@Param("sbjId") Long sbjId);
}