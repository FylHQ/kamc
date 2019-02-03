package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.devag.kamc.model.I3Subject;

public interface I3SubjectRepository extends CrudRepository<I3Subject, Long> {
   List<I3Subject> findBySbjDescription(String sbjDescription);

   //@Query("select s from I3Subject s where s.sbjDescription like = %:descr%") 
   List<I3Subject> findBySbjDescriptionIgnoreCaseContaining(@Param("descr") String descr);
   
}