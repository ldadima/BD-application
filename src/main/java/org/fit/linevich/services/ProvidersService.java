package org.fit.linevich.services;

import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.ProviderEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.views.Feed;
import org.fit.linevich.views.Provider;
import org.fit.linevich.repositories.ProvidersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProvidersService{

    @Autowired
    private ProvidersRepo providersRepo;
    @Autowired
    private CustomDataMapper customDataMapper;

    public List<Provider> showAll(){
        Iterable<ProviderEntity> providers = providersRepo.findAll();
        List<Provider> providerDTOS = new ArrayList<>();
        for (ProviderEntity providerEntity :providers){
            providerDTOS.add(customDataMapper.toProviderView(providerEntity));
        }
        return providerDTOS;
    }

    public Provider findById(int id){
        Optional<ProviderEntity> providerEntity = providersRepo.findById(id);
        return providerEntity.map(entity -> customDataMapper.toProviderView(entity)).orElse(null);
    }

    public void deleteById(int id){
        providersRepo.deleteById(id);
    }

    public void create(Provider provider){
        providersRepo.save(customDataMapper.toProviderEntity(provider));
    }

}
