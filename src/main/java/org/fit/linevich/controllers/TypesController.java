package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.fit.linevich.model.AnimalType;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.model.Development;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.model.Gender;
import org.fit.linevich.model.PhysState;
import org.fit.linevich.model.Season;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Api
@Controller
@AllArgsConstructor
@RequestMapping("/type")
@CrossOrigin(origins = "http://localhost:3000")
public class TypesController {

    @GetMapping("/gender")
    @ResponseBody
    public ResponseEntity<List<String>> gender(){
        List<String> genders = new ArrayList<>();
        for(Gender one: Gender.values()){
            genders.add(one.getName());
        }
        return ResponseEntity.ok(genders);
    }

    @GetMapping("/typeAnimal")
    @ResponseBody
    public ResponseEntity<List<String>> animalType(){
        List<String> animalTypes = new ArrayList<>();
        for(AnimalType one: AnimalType.values()){
            animalTypes.add(one.getName());
        }
        return ResponseEntity.ok(animalTypes);
    }

    @GetMapping("/climaticHabitat")
    @ResponseBody
    public ResponseEntity<List<String>> climaticZone(){
        List<String> climaticZones = new ArrayList<>();
        for(ClimaticZone one: ClimaticZone.values()){
            climaticZones.add(one.getName());
        }
        return ResponseEntity.ok(climaticZones);
    }

    @GetMapping("/employeeCategory")
    @ResponseBody
    public ResponseEntity<List<String>> employeeCategory(){
        List<String> employeeCategorys = new ArrayList<>();
        for(EmployeeCategory one: EmployeeCategory.values()){
            employeeCategorys.add(one.getName());
        }
        return ResponseEntity.ok(employeeCategorys);
    }

    @GetMapping("/development")
    @ResponseBody
    public ResponseEntity<List<String>> development(){
        List<String> developments = new ArrayList<>();
        for(Development one: Development.values()){
            developments.add(one.getName());
        }
        return ResponseEntity.ok(developments);
    }

    @GetMapping("/physicalState")
    @ResponseBody
    public ResponseEntity<List<String>> physState(){
        List<String> physStates = new ArrayList<>();
        for(PhysState one: PhysState.values()){
            physStates.add(one.getName());
        }
        return ResponseEntity.ok(physStates);
    }

    @GetMapping("/season")
    @ResponseBody
    public ResponseEntity<List<String>> season(){
        List<String> seasons = new ArrayList<>();
        for(Season one: Season.values()){
            seasons.add(one.getName());
        }
        return ResponseEntity.ok(seasons);
    }
    
}
