package org.fit.linevich.repositories;

import org.fit.linevich.domain.AnimalEntity;
import org.springframework.data.repository.CrudRepository;

public interface AnimalsRepo extends CrudRepository<AnimalEntity,Integer>{
}
