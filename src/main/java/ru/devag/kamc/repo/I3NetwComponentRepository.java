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

   @Query("select netw, obj from I3NetwComponent netw " + 
      "join I3LandComponent land on netw.land = land.id " + 
      "join I3Object obj on land.object = obj.id " + 
      "where netw.netCadastralInfo is not null")
   List<Tuple> findObjectsNetCadastralInfoNotNull();
}