package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.AnimalReceiptEntity;
import org.fit.linevich.domain.AnimalReceiptEntityPK;
import org.fit.linevich.domain.ZooEntity;
import org.fit.linevich.services.ZoosService;
import org.fit.linevich.views.Zoo;
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
@RequestMapping("/zoos")
@CrossOrigin(origins = "http://localhost:3000")
public class ZoosController {
    private final ZoosService zoosService;

    @GetMapping("/showAll")
    @ApiOperation("Show all another zoos")
    @ResponseBody
    public ResponseEntity<Page<Zoo>> showZoos(int page, int size) {
        return ResponseEntity.ok(zoosService.showAll(page, size));
    }

    @GetMapping("/zooById")
    @ApiOperation("Show zoo by id")
    @ResponseBody
    public ResponseEntity<Zoo> zooById(int id) {
        Zoo answer = zoosService.findById(id);
        if(answer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/createZoo")
    @ApiOperation("Create zoo")
    public ResponseEntity<String> createZoo(@Valid @RequestBody Zoo zoo){
        zoosService.create(zoo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Zoo added");
    }

    @PutMapping("/updateZoo")
    @ApiOperation("Update zoo")
    public ResponseEntity<String> updateZoo(@Valid @RequestBody Zoo zoo){
        if(zoosService.update(zoo)) {
            return ResponseEntity.status(HttpStatus.OK).body("Zoo updated");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Zoo not found");
        }
    }

    @DeleteMapping("/deleteZooById")
    @ApiOperation("Delete zoo")
    public ResponseEntity<String> deleteZooById(int id){
        zoosService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Zoo deleted");
    }

    @PostMapping("/addReceipt")
    public ResponseEntity<String> addReceipt(int zooId, int animalId, LocalDate date) {
        zoosService.addReceipt(zooId, animalId, date);
        return ResponseEntity.status(HttpStatus.OK).body("Receipt added");
    }

    @DeleteMapping("/deleteReceipt")
    public ResponseEntity<String> deleteReceipt(int zooId, int animalId) {
        zoosService.deleteReceipt(zooId, animalId);
        return ResponseEntity.status(HttpStatus.OK).body("Receipt deleted");
    }
}
