package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.ProviderEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.repositories.ProvidersRepo;
import org.fit.linevich.views.Provider;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProvidersService {

    private final ProvidersRepo providersRepo;
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


    /**
     * 8-ой запрос
     */
    public long count() {
        return providersRepo.count();
    }

    /**
     * 8-ой запрос
     */
    public List<Provider> supplyForPeriod( Date begin, Date end) {
        List<ProviderEntity> providerEntities = entityManager.createQuery("select p from ProviderEntity as p " +
                "join SupplyEntity as s on s.providerId = p " +
                "where s.dateSupply between :begin and :end " +
                "order by s.price", ProviderEntity.class)
                .setParameter("begin", begin)
                .setParameter("end", end)
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
