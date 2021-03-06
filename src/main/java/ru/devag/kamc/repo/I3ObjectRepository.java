package ru.devag.kamc.repo;

import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.devag.kamc.model.I3Object;

public interface I3ObjectRepository extends CrudRepository<I3Object, Long> {
   Optional<I3Object> findByObjNumber(String val);
   List<I3Object> findByObjDescriptionIgnoreCase(String objDescription);

   @Query("select obj from I3Object obj " +
      "join I3ObjRtn obr on obj.id = obr.objObjectId " + 
      "join I3SbjRtn sbr on obr.rtnRelationId = sbr.rtnRelationId " + 
      "where sbr.sbjSubjectId = :sbjId")
   List<I3Object> findByRtnSbj(@Param("sbjId") Long sbjId);

   @Query("select obj from I3Object obj")
   List<I3Object> getAll();

   @Query(nativeQuery = true, value = "select obj_id from i3_v_obj_last_cost where balance_cost = ?1")
   List<Long> getIdByLastCost(Double lastCost);

   @Query(nativeQuery = true, value = "select obj_id, balance_cost, obj_cat from i3_v_obj_last_cost join i3_v_all_object_status_light using(obj_id)")
   List<Tuple> getAllLastCost();

}