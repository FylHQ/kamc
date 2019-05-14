package ru.devag.kamc.repo;

import org.springframework.data.repository.CrudRepository;
import ru.devag.kamc.model.I3PaymMethodics;

public interface I3PaymMethodicsRepository extends CrudRepository<I3PaymMethodics, Long> {
   I3PaymMethodics findByPamCode(String code);
   
}