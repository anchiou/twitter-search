package com.search.twitter.searcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping
    public ResponseEntity hello() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity search(@RequestBody SearchObject data) {
        try {
            String query = data.getQuery();
            String lang = data.getLang();
            searchService.searcher(query, lang);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage() + e.getStackTrace());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

}