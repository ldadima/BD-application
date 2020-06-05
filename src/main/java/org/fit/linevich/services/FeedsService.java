package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.FeedNotNeedEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.repositories.FeedsRepo;
import org.fit.linevich.views.Feed;
import org.fit.linevich.views.FeedNotNeedQuery;
import org.fit.linevich.views.Zoo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FeedsService {
    private final FeedsRepo feedsRepo;
    private final CustomDataMapper customDataMapper;
    private final EntityManager entityManager;

    public Page<Feed> showAll(int page, int size){
        Page<FeedEntity> feeds = feedsRepo.findAll(PageRequest.of(page, size,
                Sort.by("id").ascending()));
        return customDataMapper.toFeedPage(feeds);
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

    /**
     * 9-ый запрос
     */
    public Page<Feed> producedYourself(int page, int size){
        Page<FeedEntity> feedEntities = feedsRepo.getFeedEntitiesByVolumeIndependentProductionGreaterThan(PageRequest.of(page, size), 0);
        return customDataMapper.toFeedPage(feedEntities);
    }

    /**
     * 9-ый запрос
     */
    public Page<FeedNotNeedQuery> notNeed(int page, int size){
        List<FeedNotNeedEntity> feedEntities = entityManager.createNativeQuery("select feeds.name as name, feeds.type as type, odd_sum.sum " +
                "* 15 + even_sum.sum * 15 as consumption, " +
                "        feeds.stock * 1000 + feeds.volume_independent_production * 1000 as produce " +
                "from feeds " +
                "       join (select feed_id, " +
                "                    SUM(quantity)             as sum, " +
                "                    DATE_PART('month', now()) as mes " +
                "             from even_day_ration " +
                "             where ((DATE_PART('month', now()) between 3 and 5) and season = 'Весна') " +
                "                or ((DATE_PART('month', now()) between 6 and 8) and season = 'Лето') " +
                "                or ((DATE_PART('month', now()) between 9 and 11) and season = 'Осень') " +
                "                or ( " +
                "                 (DATE_PART('month', now()) = 12 or DATE_PART('month', now()) = 1 or DATE_PART" +
                "('month', now()) = 2) and " +
                "                 season = 'Зима') " +
                "             group by (feed_id)) as even_sum " +
                "            on even_sum.feed_id = feeds.id " +
                "       join " +
                "     (select feed_id, " +
                "             SUM(quantity) as sum " +
                "      from odd_day_ration " +
                "      where ((DATE_PART('month', now()) between 3 and 5) and season = 'Весна') " +
                "         or ((DATE_PART('month', now()) between 6 and 8) and season = 'Лето') " +
                "         or ((DATE_PART('month', now()) between 9 and 11) and season = 'Осень') " +
                "         or ( " +
                "          (DATE_PART('month', now()) = 12 or DATE_PART('month', now()) = 1 or DATE_PART('month', now" +
                "()) = 2) and " +
                "          season = 'Зима') " +
                "      group by (feed_id)) as odd_sum " +
                "     on odd_sum.feed_id = feeds.id " +
                "where (odd_sum.sum * 15 + even_sum.sum * 15) < (feeds.stock * 1000 + feeds" +
                ".volume_independent_production * 1000);", "feedQueryMapper")
                .getResultList();
        List<FeedNotNeedQuery> needQueries = customDataMapper.toFeedQuery(feedEntities);
        return new PageImpl<>(needQueries.subList(page*size, Math.min(size * (page + 1), needQueries.size())), PageRequest.of(page, size, Sort.by("id").ascending()), needQueries.size());
    }
}
