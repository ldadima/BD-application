package org.fit.linevich.repositories;

import org.fit.linevich.domain.ZooEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ZoosRepo extends PagingAndSortingRepository<ZooEntity, Integer> {
}
