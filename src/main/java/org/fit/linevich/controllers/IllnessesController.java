package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fit.linevich.services.IllnessesService;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.Illness;
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
@RequestMapping("/illnesses")
public class IllnessesController {
    @Autowired
    private IllnessesService illnessesService;

    @GetMapping("/showAll")
    @ApiOperation("Show all illnesses(sorry that they are)")
    @ResponseBody
    public List<Illness> showIllnesses() {
        return illnessesService.showAll();
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

    @DeleteMapping("/deleteIllnessById")
    @ApiOperation("Delete illness")
    public ResponseEntity<String> deleteIllnessById(int id){
        illnessesService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Illness deleted");
    }
}
