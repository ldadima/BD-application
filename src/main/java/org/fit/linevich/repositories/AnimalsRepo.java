package org.fit.linevich.repositories;

import org.fit.linevich.domain.AnimalEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnimalsRepo extends CrudRepository<AnimalEntity, Integer> {
    @Override
    List<AnimalEntity> findAll();

    List<AnimalEntity> getAnimalEntitiesByNeedRelocationIsTrue();
}
