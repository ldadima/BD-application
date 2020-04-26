package org.fit.linevich.services;

import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.views.Employee;
import org.fit.linevich.views.Feed;
import org.fit.linevich.repositories.FeedsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedsService {
    @Autowired
    private FeedsRepo feedsRepo;
    @Autowired
    private CustomDataMapper customDataMapper;

    public List<Feed> showAll(){
        Iterable<FeedEntity> feeds = feedsRepo.findAll();
        List<Feed> feedDTOS = new ArrayList<>();
        for (FeedEntity feedEntity :feeds){
            feedDTOS.add(customDataMapper.toFeedView(feedEntity));
        }
        return feedDTOS;
    }

    public Feed findById(int id){
        Optional<FeedEntity> feedEntity = feedsRepo.findById(id);
        return feedEntity.map(entity -> customDataMapper.toFeedView(entity)).orElse(null);
    }

    public void deleteById(int id){
        feedsRepo.deleteById(id);
    }

    public void create(Feed feed){
        feedsRepo.save(customDataMapper.toFeedEntity(feed));
    }
}
