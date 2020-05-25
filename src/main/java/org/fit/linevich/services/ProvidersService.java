package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.ProviderEntity;
import org.fit.linevich.domain.ProvidersSpecializationEntity;
import org.fit.linevich.domain.ProvidersSpecializationEntityPK;
import org.fit.linevich.domain.SupplyEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.repositories.FeedsRepo;
import org.fit.linevich.repositories.ProvidersRepo;
import org.fit.linevich.views.Provider;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProvidersService {

    private final ProvidersRepo providersRepo;
    private final FeedsRepo feedsRepo;
    private final CustomDataMapper customDataMapper;
    private final EntityManager entityManager;

    public List<Provider> showAll() {
        Iterable<ProviderEntity> providers = providersRepo.findAll();
        return customDataMapper.toProviderListView(providers);
    }

    public Provider findById(int id) {
        Optional<ProviderEntity> providerEntity = providersRepo.findById(id);
        return providerEntity.map(customDataMapper::toProviderView).orElse(null);
    }

    public void deleteById(int id) {
        providersRepo.deleteById(id);
    }

    public void create(Provider provider) {
        ProviderEntity providerEntity = new ProviderEntity();
        customDataMapper.toProviderEntity(provider, providerEntity);
        providersRepo.save(providerEntity);
    }

    public boolean update(Provider provider) {
        ProviderEntity providerInBd = providersRepo.findById(provider.getProvId())
                .orElse(null);
        if (providerInBd == null) {
            return false;
        }
        customDataMapper.toProviderEntity(provider, providerInBd);
        providersRepo.save(providerInBd);
        return true;
    }

    public void addSupply(int providerId, int feedId, BigDecimal price, int amount, LocalDate date) {
        ProviderEntity providerEntity = providersRepo.findById(providerId).orElse(null);
        FeedEntity feedEntity = feedsRepo.findById(feedId).orElse(null);
        if (providerEntity == null || feedEntity == null) {
            return;
        }
        providerEntity.getSuppliesByProvId()
                .add(new SupplyEntity(null, price, amount, Date.valueOf(date), providerEntity, feedEntity));
        providersRepo.save(providerEntity);
    }

    public void deleteSupply(int providerId, int supplyId) {
        ProviderEntity providerEntity = providersRepo.findById(providerId).orElse(null);
        if (providerEntity == null) {
            return;
        }
        providerEntity.getSuppliesByProvId()
                .removeIf(supplyEntity -> supplyEntity.getId() == supplyId);
        providersRepo.save(providerEntity);
    }

    public void addSpecialization(int providerId, int feedId) {
        ProviderEntity providerEntity = providersRepo.findById(providerId).orElse(null);
        FeedEntity feedEntity = feedsRepo.findById(feedId).orElse(null);
        if (providerEntity == null || feedEntity == null) {
            return;
        }
        providerEntity.getProvidersSpecializationsByProvId()
                .add(new ProvidersSpecializationEntity(new ProvidersSpecializationEntityPK(feedId, providerId),
                        feedEntity, providerEntity));
        providersRepo.save(providerEntity);
    }

    public void deleteSpecialization(int providerId, int feedId) {
        ProviderEntity providerEntity = providersRepo.findById(providerId).orElse(null);
        if (providerEntity == null) {
            return;
        }
        providerEntity.getProvidersSpecializationsByProvId()
                .removeIf(providersSpecializationEntity ->
                        providersSpecializationEntity.getProvidersSpecializationEntityPK().getFeedId() ==
                                feedId);
        providersRepo.save(providerEntity);
    }

    /**
     * 8-ой запрос
     */
    public long count() {
        return providersRepo.count();
    }

    /**
     * 8-ой запрос
     */
    public List<Provider> supplyForPeriod(LocalDate begin, LocalDate end) {
        List<ProviderEntity> providerEntities = entityManager.createQuery("select p from ProviderEntity as p " +
                "join SupplyEntity as s on s.providerId = p " +
                "where s.dateSupply between :begin and :end " +
                "order by s.price", ProviderEntity.class)
                .setParameter("begin", Date.valueOf(begin))
                .setParameter("end", Date.valueOf(end))
                .getResultList();
        return customDataMapper.toProviderListView(providerEntities);
    }

    /**
     * 8-ой запрос
     */
    public List<Provider> specFeed(String feed) {
        List<ProviderEntity> providerEntities = entityManager.createQuery("select p from ProviderEntity as p " +
                "join ProvidersSpecializationEntity as spec on spec.providerId = p " +
                "join FeedEntity as f on f = spec.feedId " +
                "where f.name = :feed", ProviderEntity.class)
                .setParameter("feed", feed)
                .getResultList();
        return customDataMapper.toProviderListView(providerEntities);
    }
}
