package com.search.twitter.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @PostMapping
    public void search(@RequestBody String query) {
        try {

        } catch (Error e) {

        }
        return;
    }

}