package org.fit.linevich.services;

import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.model.Season;
import org.fit.linevich.views.Animal;
import org.fit.linevich.repositories.AnimalsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    @Autowired
    private AnimalsRepo animalsRepo;
    @Autowired
    private CustomDataMapper customDataMapper;


    public List<Animal> showAll(){
        Iterable<AnimalEntity> animalsList = animalsRepo.findAll();
        List<Animal> animals = new ArrayList<>();
        for(AnimalEntity animalEntity : animalsList){
            animals.add(customDataMapper.toAnimalView(animalEntity));
        }
        return animals;
    }

    public Animal findById(int id){
        Optional<AnimalEntity> animalEntity = animalsRepo.findById(id);
        return animalEntity.map(entity -> customDataMapper.toAnimalView(entity)).orElse(null);
    }

    public void create(Animal animal){
        animalsRepo.save(customDataMapper.toAnimalEntity(animal));
    }

    public void deleteById(int id){
        animalsRepo.deleteById(id);
    }

    // public void addFeedEven(int id, )
}
