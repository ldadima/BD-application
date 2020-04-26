package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fit.linevich.services.ZoosService;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.Zoo;
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
@RequestMapping("/zoos")
public class ZoosController {
    @Autowired
    private ZoosService zoosService;

    @GetMapping("/showAll")
    @ApiOperation("Show all another zoos")
    @ResponseBody
    public List<Zoo> showZoos() {
        return zoosService.showAll();
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

    @DeleteMapping("/deleteZooById")
    @ApiOperation("Delete zoo")
    public ResponseEntity<String> deleteZooById(int id){
        zoosService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Zoo deleted");
    }
}
