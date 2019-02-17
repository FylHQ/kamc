package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3RtnBst;

public interface I3RtnBstRepository extends CrudRepository<I3RtnBst, Long> {
   /*@Query(nativeQuery = true, 
      value = "select rbs.i3rbs_rtn_bst_id" +
      " from i3_rtn_bst rbs" +
      "   where rbs.i3rtn_relation_id = ?1 and" +
      "   rbs.i3bst_basement_id = ?2")
   List<Long> findRtnByRtnIdAndBstId(Long objId);*/

   //List<Long> findRtnByRtnIdAndBstId(Long rtnRelationId, Long bstBasementId)
}