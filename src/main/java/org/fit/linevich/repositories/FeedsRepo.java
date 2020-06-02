package org.fit.linevich.repositories;

import org.fit.linevich.domain.FeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FeedsRepo extends PagingAndSortingRepository<FeedEntity,Integer> {
    Page<FeedEntity> getFeedEntitiesByVolumeIndependentProductionGreaterThan(Pageable pageable, int volume);
}
