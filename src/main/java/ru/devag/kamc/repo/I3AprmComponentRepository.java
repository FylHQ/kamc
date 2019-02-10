package ru.devag.kamc.repo;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3AprmComponent;

public interface I3AprmComponentRepository extends CrudRepository<I3AprmComponent, Long> {
   List<I3AprmComponent> findByApmCadastralInfo(String apmCadastralInfo);
   List<I3AprmComponent> findByApmCadastralInfoNotNull();
   
   @Query(nativeQuery = true, value = 
      "select oba.i3obj_object_id, getshortaddress(i3add_address_id, 1, 100, 0, 0, 0) addr" +
      " from i3_aprm_component aprm" +
      " join i3_obj_add oba on aprm.i3obj_object_id = oba.i3obj_object_id")
   List<Tuple> findAllAddresses();
}