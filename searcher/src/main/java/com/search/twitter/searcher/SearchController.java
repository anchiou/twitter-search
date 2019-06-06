package com.search.twitter.searcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping
    public RestResponse hello() {
        return new RestResponse(200, "OK", null);
    }

    @PostMapping
    public ResponseEntity search(@RequestBody SearchObject data) {
        try {
            searchService.searcher(data);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage() + e.getStackTrace());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

}