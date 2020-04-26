package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fit.linevich.services.AnimalService;
import org.fit.linevich.views.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Api
@Controller
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping("/showAll")
    @ApiOperation("Show all animals")
    @ResponseBody
    public List<Animal> showAnimals() {
        return animalService.showAll();
    }

    @GetMapping("/animalById")
    @ApiOperation("Show animal by id")
    @ResponseBody
    public ResponseEntity<Animal> animalById(int id) {
        Animal answer = animalService.findById(id);
        if(answer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/createAnimal")
    @ApiOperation("Create animal")
    public ResponseEntity<String> createAnimal(@Valid @RequestBody Animal animal){
        animalService.create(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal added");
    }

    @DeleteMapping("/deleteAnimalById")
    @ApiOperation("Delete animal")
    public ResponseEntity<String> deleteAnimalById(int id){
        animalService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Animal deleted");
    }
}
