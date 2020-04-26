package org.fit.linevich.repositories;

import org.fit.linevich.domain.IllnessEntity;
import org.springframework.data.repository.CrudRepository;

public interface IllnessesRepo extends CrudRepository<IllnessEntity, Integer> {
}
