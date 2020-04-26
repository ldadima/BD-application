package org.fit.linevich.services;

import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.IllnessEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.views.Feed;
import org.fit.linevich.views.Illness;
import org.fit.linevich.repositories.IllnessesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IllnessesService {

    @Autowired
    private IllnessesRepo illnessesRepo;
    @Autowired
    private CustomDataMapper customDataMapper;

    public List<Illness> showAll(){
        Iterable<IllnessEntity> illnesses = illnessesRepo.findAll();
        List<Illness> illnessDTOS = new ArrayList<>();
        for(IllnessEntity illnessEntity :illnesses){
            illnessDTOS.add(customDataMapper.toIllnessView(illnessEntity));
        }
        return illnessDTOS;
    }

    public Illness findById(int id){
        Optional<IllnessEntity> illnessEntity = illnessesRepo.findById(id);
        return illnessEntity.map(entity -> customDataMapper.toIllnessView(entity)).orElse(null);
    }

    public void deleteById(int id){
        illnessesRepo.deleteById(id);
    }

    public void create(Illness illness){
        illnessesRepo.save(customDataMapper.toIllnessEntity(illness));
    }

}
