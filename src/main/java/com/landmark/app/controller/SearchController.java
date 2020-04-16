package com.landmark.app.controller;

import com.landmark.app.model.dto.SearchDTO;
import com.landmark.app.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.landmark.app.utils.Constants.SEARCH_API;

@RestController
@RequestMapping(value = SEARCH_API)
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping(value = "/keyword")
    public ResponseEntity<?> searchKeyword(@RequestBody SearchDTO.SearchKeywordDTO searchKeywordDTO) {
        return new ResponseEntity<>(searchService.searchKeyword(searchKeywordDTO), HttpStatus.OK);
    }
}
