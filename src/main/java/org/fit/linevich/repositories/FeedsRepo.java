package org.fit.linevich.repositories;

import org.fit.linevich.domain.FeedEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeedsRepo extends CrudRepository<FeedEntity,Integer> {
    List<FeedEntity> getFeedEntitiesByVolumeIndependentProductionGreaterThan(int volume);
}
