package ru.devag.kamc.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.devag.kamc.model.I3ClassifierValue;

public interface I3ClassifierValueRepository extends CrudRepository<I3ClassifierValue, Long> {
   I3ClassifierValue findByCfvCode(String cfvCode);
   List<I3ClassifierValue> findByCfvValueAndCfvParentId(String value, Long parentId);
}