package org.fit.linevich.repositories;

import org.fit.linevich.domain.FeedEntity;
import org.springframework.data.repository.CrudRepository;

public interface FeedsRepo extends CrudRepository<FeedEntity,Integer> {
}
