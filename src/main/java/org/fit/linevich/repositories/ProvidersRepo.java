package org.fit.linevich.repositories;

import org.fit.linevich.domain.ProviderEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProvidersRepo extends CrudRepository<ProviderEntity,Integer> {
}
