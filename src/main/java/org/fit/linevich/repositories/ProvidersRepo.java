package org.fit.linevich.repositories;

import org.fit.linevich.domain.ProviderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProvidersRepo extends PagingAndSortingRepository<ProviderEntity,Integer> {
}
