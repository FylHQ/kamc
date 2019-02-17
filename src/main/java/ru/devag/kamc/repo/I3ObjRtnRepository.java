package ru.devag.kamc.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3ObjRtn;

public interface I3ObjRtnRepository extends CrudRepository<I3ObjRtn, Long> {

   @Query(nativeQuery = true, 
      value = "select obr.*" + 
      " from i3_obj_rtn obr," +
      "   i3_relation rtn," +
      "   i3_titl_component titl" +
      " where obr.i3obj_object_id = ?1 and" +
      "   obr.i3rtn_relation_id = rtn.i3rtn_relation_id and" +
      "   rtn.i3sts_status_id = 2 and" +
      "   rtn.i3rtn_relation_id = titl.i3rtn_relation_id and" +
      "   titl.i3cfv_classifier_value_id = i3_kernel_engine.GetClfIDOrCode('TITTYPE_LEASE')")
   List<I3ObjRtn> findActualRentByObjId(Long objId);
   
   List<I3ObjRtn> findByObjObjectIdAndObrType(Long objObjectId, Long obrType);
   
}