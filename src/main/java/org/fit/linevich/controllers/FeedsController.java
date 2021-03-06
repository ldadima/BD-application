package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fit.linevich.services.FeedsService;
import org.fit.linevich.views.Feed;
import org.fit.linevich.views.FeedNotNeedQuery;
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
@RequestMapping("/feeds")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedsController {
    private final FeedsService feedsService;

    @GetMapping("/showAll")
    @ApiOperation("Show all feeds in zoo")
    @ResponseBody
    public ResponseEntity<Page<Feed>> showFeeds(int page, int size) {
        return ResponseEntity.ok(feedsService.showAll(page, size));
    }


    @GetMapping("/producedMyself")
    @ApiOperation("Show feeds produced by zoo yourself")
    @ResponseBody
    public ResponseEntity<Page<Feed>> producedYourself(int page, int size) {
        Page<Feed> feeds = feedsService.producedYourself(page, size);
        return ResponseEntity.ok(feeds);
    }


    @GetMapping("/notNeed")
    @ApiOperation("Show feeds not need for buy")
    @ResponseBody
    public ResponseEntity<Page<FeedNotNeedQuery>> notNeed(int page, int size) {
        Page<FeedNotNeedQuery> feeds = feedsService.notNeed(page, size);
        return ResponseEntity.ok(feeds);
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

    @PutMapping("/updateFeed")
    @ApiOperation("Update feed")
    public ResponseEntity<String> updateFeed(@Valid @RequestBody Feed feed){
        if(feedsService.update(feed)) {
            return ResponseEntity.status(HttpStatus.OK).body("Feed updated");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Feed not found");
        }
    }

    @DeleteMapping("/deleteFeedById")
    @ApiOperation("Delete feed")
    public ResponseEntity<String> deleteFeedById(int id){
        feedsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Feed deleted");
    }
}
