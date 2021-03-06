package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fit.linevich.services.IllnessesService;
import org.fit.linevich.views.Illness;
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
import java.util.List;

@Api
@Controller
@AllArgsConstructor
@RequestMapping("/illnesses")
@CrossOrigin(origins = "http://localhost:3000")
public class IllnessesController {
    private final IllnessesService illnessesService;

    @GetMapping("/showAll")
    @ApiOperation("Show all illnesses(sorry that they are)")
    @ResponseBody
    public ResponseEntity<Page<Illness>> showIllnesses(int page, int size) {
        return ResponseEntity.ok(illnessesService.showAll(page, size));
    }

    @GetMapping("/illnessById")
    @ApiOperation("Show illness by id")
    @ResponseBody
    public ResponseEntity<Illness> illnessById(int id) {
        Illness answer = illnessesService.findById(id);
        if(answer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/createIllness")
    @ApiOperation("Create illness")
    public ResponseEntity<String> createIllness(@Valid @RequestBody Illness illness){
        illnessesService.create(illness);
        return ResponseEntity.status(HttpStatus.CREATED).body("Illness added");
    }

    @PutMapping("/updateIllness")
    @ApiOperation("Update illness")
    public ResponseEntity<String> updateIllness(@Valid @RequestBody Illness illness){
        if(illnessesService.update(illness)) {
            return ResponseEntity.status(HttpStatus.OK).body("Illness updated");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Illness not found");
        }
    }

    @DeleteMapping("/deleteIllnessById")
    @ApiOperation("Delete illness")
    public ResponseEntity<String> deleteIllnessById(int id){
        illnessesService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Illness deleted");
    }
}
