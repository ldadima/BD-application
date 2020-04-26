package org.fit.linevich.services;

import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.ZooEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.views.Feed;
import org.fit.linevich.views.Zoo;
import org.fit.linevich.repositories.ZoosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ZoosService {

    @Autowired
    private ZoosRepo zoosRepo;
    @Autowired
    private CustomDataMapper customDataMapper;

    public List<Zoo> showAll(){
        Iterable<ZooEntity> zoos = zoosRepo.findAll();
        List<Zoo> zooDTOS = new ArrayList<>();
        for (ZooEntity zooEntity :zoos){
            zooDTOS.add(customDataMapper.toZooView(zooEntity));
        }
        return zooDTOS;
    }

    public Zoo findById(int id){
        Optional<ZooEntity> zooEntity = zoosRepo.findById(id);
        return zooEntity.map(entity -> customDataMapper.toZooView(entity)).orElse(null);
    }

    public void deleteById(int id){
        zoosRepo.deleteById(id);
    }

    public void create(Zoo zoo){
        zoosRepo.save(customDataMapper.toZooEntity(zoo));
    }

}
