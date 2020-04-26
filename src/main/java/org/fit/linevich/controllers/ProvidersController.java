package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fit.linevich.services.ProvidersService;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.Provider;
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
@RequestMapping("/providers")
public class ProvidersController {
    @Autowired
    private ProvidersService providersService;

    @GetMapping("/showAll")
    @ApiOperation("Show all providers")
    @ResponseBody
    public List<Provider> showProviders() {
        return providersService.showAll();
    }

    @GetMapping("/providerById")
    @ApiOperation("Show provider by id")
    @ResponseBody
    public ResponseEntity<Provider> providerById(int id) {
        Provider answer = providersService.findById(id);
        if(answer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/createProvider")
    @ApiOperation("Create provider")
    public ResponseEntity<String> createProvider(@Valid @RequestBody Provider provider){
        providersService.create(provider);
        return ResponseEntity.status(HttpStatus.CREATED).body("Provider added");
    }

    @DeleteMapping("/deleteProviderById")
    @ApiOperation("Delete provider")
    public ResponseEntity<String> deleteProviderById(int id){
        providersService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Provider deleted");
    }
}
