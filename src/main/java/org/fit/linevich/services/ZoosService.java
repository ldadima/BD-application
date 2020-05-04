package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.ZooEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.repositories.ZoosRepo;
import org.fit.linevich.views.Zoo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ZoosService {

    private final ZoosRepo zoosRepo;
    private final CustomDataMapper customDataMapper;

    public List<Zoo> showAll(){
        Iterable<ZooEntity> zoos = zoosRepo.findAll();
        return customDataMapper.toZooListView(zoos);
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
}
