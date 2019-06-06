package com.search.twitter.searcher;

import com.search.twitter.searcher.entity.SearchObject;
import com.search.twitter.searcher.entity.TweetObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public RestResponse search(@RequestBody SearchObject data) {
        List<TweetObject> result = null;
        try {
            result = searchService.searcher(data);
        } catch (IOException e) {
            return new RestResponse(400, "Error: " + e.getMessage() + e.getStackTrace(), result);
        }
        return new RestResponse(200, "Success", result);
    }

}