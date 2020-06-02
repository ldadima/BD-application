package org.fit.linevich.repositories;

import org.fit.linevich.domain.IllnessEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IllnessesRepo extends PagingAndSortingRepository<IllnessEntity, Integer> {
}
