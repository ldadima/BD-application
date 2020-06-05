package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.AnimalReceiptEntity;
import org.fit.linevich.domain.AnimalReceiptEntityPK;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.ProviderEntity;
import org.fit.linevich.domain.ProvidersSpecializationEntity;
import org.fit.linevich.domain.ProvidersSpecializationEntityPK;
import org.fit.linevich.domain.ZooEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.repositories.AnimalsRepo;
import org.fit.linevich.repositories.ZoosRepo;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.Zoo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ZoosService {

    private final ZoosRepo zoosRepo;
    private final AnimalsRepo animalsRepo;
    private final CustomDataMapper customDataMapper;
    private final EntityManager entityManager;

    public Page<Zoo> showAll(int page, int size){
        Page<ZooEntity> zoos = zoosRepo.findAll(PageRequest.of(page, size,
                Sort.by("id").ascending()));
        return customDataMapper.toZooPage(zoos);
    }

    public Zoo findById(int id){
        Optional<ZooEntity> zooEntity = zoosRepo.findById(id);
        return zooEntity.map(customDataMapper::toZooView).orElse(null);
    }

    public void deleteById(int id){
        zoosRepo.deleteById(id);
    }

    public void create(Zoo zoo){
        ZooEntity zooEntity = new ZooEntity();
        customDataMapper.toZooEntity(zoo, zooEntity);
        zoosRepo.save(zooEntity);
    }

    public boolean update(Zoo zoo){
        ZooEntity zooInBd = zoosRepo.findById(zoo.getId())
                .orElse(null);
        if (zooInBd == null){
            return false;
        }
        customDataMapper.toZooEntity(zoo, zooInBd);
        zoosRepo.save(zooInBd);
        return true;
    }

    public void addReceipt(int zooId, int animalId, LocalDate date) {
        ZooEntity zooEntity = zoosRepo.findById(zooId).orElse(null);
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (zooEntity == null || animalEntity == null) {
            return;
        }
        zooEntity.getAnimalReceiptsById()
                .add(new AnimalReceiptEntity(new AnimalReceiptEntityPK(zooId, animalId), Date.valueOf(date), zooEntity, animalEntity));
        zoosRepo.save(zooEntity);
    }

    public void deleteReceipt(int zooId, int animalId) {
        ZooEntity zooEntity = zoosRepo.findById(zooId).orElse(null);
        if (zooEntity == null) {
            return;
        }
        zooEntity.getAnimalReceiptsById()
                .removeIf(animalReceiptEntity ->
                        animalReceiptEntity.getAnimalReceiptEntityPK().getAnimalId() ==
                                animalId);
        zoosRepo.save(zooEntity);
    }

    /**
     * 13-ый запрос
     */
    public Page<Zoo> haveChange(int page, int size) {
        List<ZooEntity> zooEntities = entityManager.createQuery("select distinct  z from AnimalReceiptEntity as ar " +
                "join ZooEntity as z on z = ar.zooId ", ZooEntity.class)
                .getResultList();
        List<Zoo> zoos = customDataMapper.toZooListView(zooEntities);
        return new PageImpl<>(zoos.subList(page*size, Math.min(size * (page + 1), zoos.size())), PageRequest.of(page, size, Sort.by("id").ascending()), zoos.size());
    }
}
