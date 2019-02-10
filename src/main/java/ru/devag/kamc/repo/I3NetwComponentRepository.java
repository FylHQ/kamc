package ru.devag.kamc.repo;

import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ru.devag.kamc.model.I3NetwComponent;

public interface I3NetwComponentRepository extends CrudRepository<I3NetwComponent, Long> {
   //List<I3NetwComponent> findByNetCadastralInfo(String netCadastralInfo);
   Optional<I3NetwComponent> findByLandObjectObjNumber(String val);

   List<I3NetwComponent> findByNetCadastralCostNotNull();

   @Query("select netw, obj from I3NetwComponent netw" + 
      " join I3LandComponent land on netw.land = land.id" + 
      " join I3Object obj on land.object = obj.id" + 
      " where netw.netCadastralInfo is not null")
   List<Tuple> findObjectsNetCadastralInfoNotNull();

   @Query(nativeQuery = true, value = 
      "select oba.i3obj_object_id, getshortaddress(i3add_address_id, 1, 100, 0, 0, 0) addr" +
      " from i3_netw_component netw" +
      " join i3_land_component land on netw.i3lnd_land_component_id = land.i3lnd_land_component_id" +
      " join i3_obj_add oba on land.i3obj_object_id = oba.i3obj_object_id")
   List<Tuple> findAllAddresses();

}