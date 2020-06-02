package org.fit.linevich.repositories;

import org.fit.linevich.domain.AnimalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AnimalsRepo extends PagingAndSortingRepository<AnimalEntity, Integer> {
    @Override
    Page<AnimalEntity> findAll(Pageable pageable);

    Page<AnimalEntity> getAnimalEntitiesByNeedRelocationIsTrue(Pageable pageable);
}
