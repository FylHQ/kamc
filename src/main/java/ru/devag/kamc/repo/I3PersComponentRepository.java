package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.devag.kamc.model.I3PersComponent;

public interface I3PersComponentRepository extends CrudRepository<I3PersComponent, Long> {
   List<I3PersComponent> findByPrsItn(String prsItn);
   
   @Query("select pers" +
      " from I3PersComponent pers" + 
      " join I3SbjBst sbb on pers.sbj = sbb.sbjSubjectId" + 
      " join I3CertComponent cert on sbb.bstBasementId = cert.bstBasementId" + 
      " where sbb.sbbType = 4104 and cert.cerOgrn = :ogrn")
   List<I3PersComponent> findByCertOgrn(@Param("ogrn") String ogrn);
}