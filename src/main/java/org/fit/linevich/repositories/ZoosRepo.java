package org.fit.linevich.repositories;

import org.fit.linevich.domain.ZooEntity;
import org.springframework.data.repository.CrudRepository;

public interface ZoosRepo extends CrudRepository<ZooEntity, Integer> {
}
