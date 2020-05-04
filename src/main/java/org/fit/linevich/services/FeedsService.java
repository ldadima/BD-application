package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.repositories.FeedsRepo;
import org.fit.linevich.views.Feed;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FeedsService {
    private final FeedsRepo feedsRepo;
    private final CustomDataMapper customDataMapper;

    public List<Feed> showAll(){
        Iterable<FeedEntity> feeds = feedsRepo.findAll();
        return customDataMapper.toFeedListView(feeds);
    }

    public Feed findById(int id){
        Optional<FeedEntity> feedEntity = feedsRepo.findById(id);
        return feedEntity.map(customDataMapper::toFeedView).orElse(null);
    }

    public void deleteById(int id){
        feedsRepo.deleteById(id);
    }

    public void create(Feed feed){
        FeedEntity feedEntity = new FeedEntity();
        customDataMapper.toFeedEntity(feed, feedEntity);
        feedsRepo.save(feedEntity);
    }

    public boolean update(Feed feed){
        FeedEntity feedInBd = feedsRepo.findById(feed.getId())
                .orElse(null);
        if (feedInBd == null){
            return false;
        }
        customDataMapper.toFeedEntity(feed, feedInBd);
        feedsRepo.save(feedInBd);
        return true;
    }
}
