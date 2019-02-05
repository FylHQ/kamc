package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.devag.kamc.model.I3Subject;

public interface I3SubjectRepository extends CrudRepository<I3Subject, Long> {
   List<I3Subject> findBySbjDescription(String sbjDescription);

   List<I3Subject> findBySbjDescriptionIgnoreCaseContaining(String descr);
}