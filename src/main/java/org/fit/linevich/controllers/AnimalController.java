package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fit.linevich.domain.AnimalCompatibilityEntity;
import org.fit.linevich.domain.AnimalCompatibilityEntityPK;
import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.CellsAnimalsEntity;
import org.fit.linevich.domain.EvenDayRationEntity;
import org.fit.linevich.domain.EvenDayRationEntityPK;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.IllnessAnimalsEntity;
import org.fit.linevich.domain.IllnessAnimalsEntityPK;
import org.fit.linevich.domain.IllnessEntity;
import org.fit.linevich.domain.MedCardEntity;
import org.fit.linevich.domain.OddDayRationEntity;
import org.fit.linevich.domain.OddDayRationEntityPK;
import org.fit.linevich.domain.VaccineEntity;
import org.fit.linevich.domain.VaccineEntityPK;
import org.fit.linevich.model.Development;
import org.fit.linevich.model.Season;
import org.fit.linevich.services.AnimalService;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.AnimalCellQuery;
import org.fit.linevich.views.AnimalMed;
import org.fit.linevich.views.FullInfoAnimalQuery;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Api
@Controller
@AllArgsConstructor
@RequestMapping("/animals")
@CrossOrigin(origins = "http://localhost:3000")
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("/showAll")
    @ApiOperation("Show all animals")
    @ResponseBody
    public ResponseEntity<Page<Animal>> showAnimals(int page, int size) {
        return ResponseEntity.ok(animalService.showAll(page, size));
    }

    @GetMapping("/animalById")
    @ApiOperation("Show animal by id")
    @ResponseBody
    public ResponseEntity<AnimalMed> animalById(int id) {
        AnimalMed answer = animalService.findById(id);
        if(answer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/animalCellLife")
    @ApiOperation("Show animals all time live in cell")
    @ResponseBody
    public ResponseEntity<Page<AnimalCellQuery>> animalCellLife(int page, int size, int cellId) {
        Page<AnimalCellQuery> answer = animalService.animalsCellLife(page, size, cellId);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/needWarmAnimals")
    @ApiOperation("Show animals need heating cell by age")
    @ResponseBody
    public ResponseEntity<Page<Animal>> needWarmAnimals(int page, int size, int age) {
        Page<Animal> answer = animalService.needWarmAnimalsByAge(page, size, age);
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/givenIllnessAnimals")
    @ApiOperation("Show animals had given illness")
    @ResponseBody
    public ResponseEntity<Page<Animal>> givenIllnessAnimals(int page, int size, String illness) {
        Page<Animal> answer = animalService.givenIllnessAnimals(page, size, illness);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/givenVaccineAnimals")
    @ApiOperation("Show animals have given vaccine")
    @ResponseBody
    public ResponseEntity<Page<Animal>> givenVaccineAnimals(int page, int size, String vaccine) {
        Page<Animal> answer = animalService.givenVaccineAnimals(page, size, vaccine);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/compatibilityKindAnimals")
    @ApiOperation("Show animals have compatibility with given kind animal ")
    @ResponseBody
    public ResponseEntity<Page<Animal>> compatibilityKindAnimals(int page, int size, String kind) {
        Page<Animal> answer = animalService.compatibilityKindAnimals(page, size, kind);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/needRelocationAnimals")
    @ApiOperation("Show animals need relocation")
    @ResponseBody
    public ResponseEntity<Page<Animal>> needRelocationAnimals(int page, int size) {
        Page<Animal> answer = animalService.needRelocationAnimals(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/needWarmCellAnimals")
    @ApiOperation("Show animals need heating cell")
    @ResponseBody
    public ResponseEntity<Page<Animal>> needWarmCellAnimals(int page, int size) {
        Page<Animal> answer = animalService.needWarmCellAnimals(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @GetMapping("/needFeed")
    @ApiOperation("Show animals need given feed in given season")
    @ResponseBody
    public ResponseEntity<Page<Animal>> needFeed(int page, int size, String feed, Season season) {
        Page<Animal> answer = animalService.needFeed(page, size, feed, season);
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

    @GetMapping("/expectedChild")
    @ApiOperation("Show animal, expected child")
    @ResponseBody
    public ResponseEntity<Page<Animal>> expectedChild(int page, int size) {
        Page<Animal> answer = animalService.expectedChild(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/createAnimal")
    @ApiOperation("Create animal")
    public ResponseEntity<String> createAnimal(@Valid @RequestBody AnimalMed animalMed){
        animalService.create(animalMed);
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal added");
    }

    @PutMapping("/updateAnimal")
    @ApiOperation("Update animal")
    public ResponseEntity<String> updateAnimal(@Valid @RequestBody AnimalMed animal){
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

    @PostMapping("/addInfoCell")
    public ResponseEntity<String> addInfoCell(int cellId, int animalId, Date begin, Date end, boolean heating) {
        animalService.addInfoCell(cellId, animalId, begin, end, heating);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cell info added");
    }

    @DeleteMapping("/deleteInfoCell")
    public ResponseEntity<String> deleteInfoCell(int cellId, int animalId) {
        animalService.deleteInfoCell(cellId, animalId);
        return ResponseEntity.status(HttpStatus.OK).body("Cell info deleted");
    }

    @PostMapping("/addOddDayRation")
    public ResponseEntity<String> addOddDayRation(int animalId, Season season, int feedId, int quantity) {
        animalService.addOddDayRation(animalId, season, feedId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Food for odd day ration added");
    }

    @DeleteMapping("/deleteOddDayRation")
    public ResponseEntity<String> deleteOddDayRation(int animalId, Season season) {
        animalService.deleteOddDayRation(animalId, season);
        return ResponseEntity.status(HttpStatus.OK).body("Food for odd day ration deleted");
    }

    @PostMapping("/addEvenDayRation")
    public ResponseEntity<String> addEvenDayRation(int animalId, Season season, int feedId, int quantity) {
        animalService.addEvenDayRation(animalId, season, feedId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Food for even day ration added");
    }

    @DeleteMapping("/deleteEvenDayRation")
    public ResponseEntity<String> deleteEvenDayRation(int animalId, Season season) {
        animalService.deleteEvenDayRation(animalId, season);
        return ResponseEntity.status(HttpStatus.OK).body("Food for even day ration deleted");
    }

    @PostMapping("/addIllness")
    public ResponseEntity<String> addIllness(int animalId, int illnessId, LocalDate dateBegin, LocalDate dateEnd) {
        animalService.addIllness(animalId, illnessId, dateBegin, dateEnd);
        return ResponseEntity.status(HttpStatus.OK).body("Illness for animal added");
    }

    @DeleteMapping("/deleteIllness")
    public ResponseEntity<String> deleteIllness(int animalId, int illnessId) {
        animalService.deleteIllness(animalId, illnessId);
        return ResponseEntity.status(HttpStatus.OK).body("Illness for animal deleted");
    }

    @PostMapping("/addVaccine")
    public ResponseEntity<String> addVaccine(int animalId, int vaccineId, String name, int dose, LocalDate date) {
        animalService.addVaccine(animalId, vaccineId, name, dose, date);
        return ResponseEntity.status(HttpStatus.OK).body("Vaccine for animal added");
    }

    @DeleteMapping("/deleteVaccine")
    public ResponseEntity<String> deleteVaccine(int animalId, int vaccineId) {
        animalService.deleteVaccine(animalId, vaccineId);
        return ResponseEntity.status(HttpStatus.OK).body("Vaccine for animal deleted");
    }

    @PostMapping("/addCompatibility")
    public ResponseEntity<String> addCompatibility(int animalId, String kind) {
        animalService.addCompatibility(animalId, kind);
        return ResponseEntity.status(HttpStatus.OK).body("Kind compatibility for animal added");
    }

    @DeleteMapping("/deleteCompatibility")
    public ResponseEntity<String> deleteCompatibility(int animalId, String kind) {
        animalService.deleteCompatibility(animalId, kind);
        return ResponseEntity.status(HttpStatus.OK).body("Kind compatibility for animal deleted");
    }
}
