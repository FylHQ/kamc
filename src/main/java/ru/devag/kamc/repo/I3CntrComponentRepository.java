package ru.devag.kamc.repo;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.devag.kamc.model.I3CntrComponent;

public interface I3CntrComponentRepository extends CrudRepository<I3CntrComponent, Long> {
   Page<I3CntrComponent> findAll(Pageable pageable);
   
   @Query("select sbb, cntr" +
   " from I3CntrComponent cntr" + 
   " join I3SbjBst sbb on cntr.bst = sbb.bstBasementId" + 
   " where sbb.sbbType = 4109 and sbb.sbjSubjectId = :sbjId")
   List<Tuple> findSbbBySbjId(@Param("sbjId") Long sbjId);

   @Query("select cntr" +
   " from I3CntrComponent cntr" + 
   " join I3BstBst bsb on cntr.bst = bsb.bstParentId" + 
   " where bsb.bsbChildId = :paymBstId")
   List<I3CntrComponent> findByPaymBstId(@Param("paymBstId") Long paymBstId);
}