package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.ProviderEntity;
import org.fit.linevich.domain.ProvidersSpecializationEntity;
import org.fit.linevich.domain.ProvidersSpecializationEntityPK;
import org.fit.linevich.domain.SupplyEntity;
import org.fit.linevich.services.ProvidersService;
import org.fit.linevich.views.Provider;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Api
@Controller
@AllArgsConstructor
@RequestMapping("/providers")
@CrossOrigin(origins = "http://localhost:3000")
public class ProvidersController {
    private final ProvidersService providersService;

    @GetMapping("/showAll")
    @ApiOperation("Show all providers")
    @ResponseBody
    public ResponseEntity<Page<Provider>> showProviders(int page, int size) {
        return ResponseEntity.ok(providersService.showAll(page, size));
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

    @PutMapping("/updateProvider")
    @ApiOperation("Update provider")
    public ResponseEntity<String> updateProvider(@Valid @RequestBody Provider provider){
        if(providersService.update(provider)) {
            return ResponseEntity.status(HttpStatus.OK).body("Provider updated");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Provider not found");
        }
    }

    @DeleteMapping("/deleteProviderById")
    @ApiOperation("Delete provider")
    public ResponseEntity<String> deleteProviderById(int id){
        providersService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Provider deleted");
    }

    @GetMapping("/count")
    @ApiOperation("Count of providers")
    public ResponseEntity<Long> count(){
        return ResponseEntity.ok(providersService.count());
    }

    @GetMapping("/supplyForPeriod")
    @ApiOperation("Providers who give feed in this period")
    public ResponseEntity<List<Provider>> supplyForPeriod(LocalDate begin, LocalDate end){
        List<Provider> providers = providersService.supplyForPeriod(begin, end);
        return ResponseEntity.ok(providers);
    }

    @GetMapping("/specOfFeed")
    @ApiOperation("Providers who give only given feed")
    public ResponseEntity<List<Provider>> specOfFeed(String feed){
        List<Provider> providers = providersService.specFeed(feed);
        return ResponseEntity.ok(providers);
    }

    @PostMapping("/addSupply")
    public ResponseEntity<String> addSupply(int providerId, int feedId, BigDecimal price, int amount, LocalDate date) {
        providersService.addSupply(providerId, feedId, price, amount, date);
        return ResponseEntity.status(HttpStatus.OK).body("Supply added");
    }

    @DeleteMapping("/deleteSupply")
    public ResponseEntity<String> deleteSupply(int providerId, int supplyId) {
        providersService.deleteSupply(providerId, supplyId);
        return ResponseEntity.status(HttpStatus.OK).body("Supply deleted");
    }

    @PostMapping("/addSpecialization")
    public ResponseEntity<String> addSpecialization(int providerId, int feedId) {
        providersService.addSpecialization(providerId, feedId);
        return ResponseEntity.status(HttpStatus.OK).body("Specialization for provider added");
    }

    @DeleteMapping("/deleteSpecialization")
    public ResponseEntity<String> deleteSpecialization(int providerId, int feedId) {
        providersService.addSpecialization(providerId, feedId);
        return ResponseEntity.status(HttpStatus.OK).body("Specialization for provider deleted");
    }
}
