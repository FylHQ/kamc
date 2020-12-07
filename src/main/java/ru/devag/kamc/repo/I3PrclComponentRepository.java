package ru.devag.kamc.repo;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3PrclComponent;

public interface I3PrclComponentRepository extends CrudRepository<I3PrclComponent, Long> {
   @Query(nativeQuery = true, value = 
      "select i3prc_prcl_component_id," +
      " i3prc_cadastral_number," + 
      " (select max(to_number(to_char(i3pco_date, 'YYYY'))) from i3_prcl_cost" +
      " where i3_prcl_cost.i3prc_prcl_component_id = i3_prcl_component.i3prc_prcl_component_id) last_year" +
      " from i3_prcl_component")
   List<Tuple> findAllWithLastCostYear();
}