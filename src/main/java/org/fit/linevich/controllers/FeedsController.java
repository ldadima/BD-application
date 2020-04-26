package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fit.linevich.services.FeedsService;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.Feed;
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
@RequestMapping("/feeds")
public class FeedsController {
    @Autowired
    private FeedsService feedsService;

    @GetMapping("/showAll")
    @ApiOperation("Show all feeds in zoo")
    @ResponseBody
    public List<Feed> showFeeds() {
        return feedsService.showAll();
    }

    @GetMapping("/feedById")
    @ApiOperation("Show feed by id")
    @ResponseBody
    public ResponseEntity<Feed> feedById(int id) {
        Feed answer = feedsService.findById(id);
        if(answer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/createFeed")
    @ApiOperation("Create feed")
    public ResponseEntity<String> createFeed(@Valid @RequestBody Feed feed){
        feedsService.create(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body("Feed added");
    }

    @DeleteMapping("/deleteFeedById")
    @ApiOperation("Delete feed")
    public ResponseEntity<String> deleteFeedById(int id){
        feedsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Feed deleted");
    }
}
