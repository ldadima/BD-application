package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.IllnessEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.repositories.IllnessesRepo;
import org.fit.linevich.views.Illness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IllnessesService {

    private final IllnessesRepo illnessesRepo;
    private final CustomDataMapper customDataMapper;

    public Page<Illness> showAll(int page, int size){
        Page<IllnessEntity> illnesses = illnessesRepo.findAll(PageRequest.of(page, size,
                Sort.by("id").ascending()));
        return customDataMapper.toIllnessPage(illnesses);
    }

    public Illness findById(int id){
        Optional<IllnessEntity> illnessEntity = illnessesRepo.findById(id);
        return illnessEntity.map(customDataMapper::toIllnessView).orElse(null);
    }

    public void deleteById(int id){
        illnessesRepo.deleteById(id);
    }

    public void create(Illness illness){
        IllnessEntity illnessEntity = new IllnessEntity();
        customDataMapper.toIllnessEntity(illness, illnessEntity);
        illnessesRepo.save(illnessEntity);
    }

    public boolean update(Illness illness){
        IllnessEntity illnessInBd = illnessesRepo.findById(illness.getId())
                .orElse(null);
        if (illnessInBd == null){
            return false;
        }
        customDataMapper.toIllnessEntity(illness, illnessInBd);
        illnessesRepo.save(illnessInBd);
        return true;
    }
}
