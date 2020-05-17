package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fit.linevich.services.AnimalService;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.AnimalCellQuery;
import org.fit.linevich.views.FullInfoAnimalQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Api
@Controller
@AllArgsConstructor
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;

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

    @GetMapping("/animalCellLife")
    @ApiOperation("Show animals all time live in cell")
    @ResponseBody
    public ResponseEntity<List<AnimalCellQuery>> animalCellLife(int cellId) {
        List<AnimalCellQuery> answer = animalService.animalsCellLife(cellId);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/needWarmAnimals")
    @ApiOperation("Show animals need heating cell by age")
    @ResponseBody
    public ResponseEntity<List<Animal>> needWarmAnimals(int age) {
        List<Animal> answer = animalService.needWarmAnimalsByAge(age);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/givenIllnessAnimals")
    @ApiOperation("Show animals had given illness")
    @ResponseBody
    public ResponseEntity<List<Animal>> givenIllnessAnimals(String illness) {
        List<Animal> answer = animalService.givenIllnessAnimals(illness);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/givenVaccineAnimals")
    @ApiOperation("Show animals have given vaccine")
    @ResponseBody
    public ResponseEntity<List<Animal>> givenVaccineAnimals(String vaccine) {
        List<Animal> answer = animalService.givenVaccineAnimals(vaccine);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/compatibilityKindAnimals")
    @ApiOperation("Show animals have compatibility with given kind animal ")
    @ResponseBody
    public ResponseEntity<List<Animal>> compatibilityKindAnimals(String kind) {
        List<Animal> answer = animalService.compatibilityKindAnimals(kind);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/needRelocationAnimals")
    @ApiOperation("Show animals need relocation")
    @ResponseBody
    public ResponseEntity<List<Animal>> needRelocationAnimals() {
        List<Animal> answer = animalService.needRelocationAnimals();
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/needWarmCellAnimals")
    @ApiOperation("Show animals need heating cell")
    @ResponseBody
    public ResponseEntity<List<Animal>> needWarmCellAnimals() {
        List<Animal> answer = animalService.needWarmCellAnimals();
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/needFeed")
    @ApiOperation("Show animals need given feed in given season")
    @ResponseBody
    public ResponseEntity<List<Animal>> needFeed(String feed, String season) {
        List<Animal> answer = animalService.needFeed(feed, season);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/fullInfoByKind")
    @ApiOperation("Show full info about animal by kind")
    @ResponseBody
    public ResponseEntity<List<FullInfoAnimalQuery>> fullInfoByKind(String kind) {
        List<FullInfoAnimalQuery> answer = animalService.fullInfoByKind(kind);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/fullInfoByVaccine")
    @ApiOperation("Show full info about animal by vaccine")
    @ResponseBody
    public ResponseEntity<List<FullInfoAnimalQuery>> fullInfoByVaccine(String vaccine) {
        List<FullInfoAnimalQuery> answer = animalService.fullInfoByVaccine(vaccine);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/createAnimal")
    @ApiOperation("Create animal")
    public ResponseEntity<String> createAnimal(@Valid @RequestBody Animal animal){
        animalService.create(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal added");
    }

    @PutMapping("/updateAnimal")
    @ApiOperation("Update animal")
    public ResponseEntity<String> updateAnimal(@Valid @RequestBody Animal animal){
        if(animalService.update(animal)) {
            return ResponseEntity.status(HttpStatus.OK).body("Animal updated");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Animal not found");
        }
    }

    @DeleteMapping("/deleteAnimalById")
    @ApiOperation("Delete animal")
    public ResponseEntity<String> deleteAnimalById(int id){
        animalService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Animal deleted");
    }
}
