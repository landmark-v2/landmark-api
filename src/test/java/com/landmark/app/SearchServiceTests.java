package com.landmark.app;

import com.landmark.app.model.dto.SearchDTO;
import com.landmark.app.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SearchServiceTests {

    private SearchService searchService;

    @Autowired
    public SearchServiceTests(SearchService searchService) {
        this.searchService = searchService;
    }

    @Test
    public void searchKeywordTest() {
        String keyword = "레저";

        SearchDTO.SearchKeywordDTO searchKeywordDTO = new SearchDTO.SearchKeywordDTO();
        searchKeywordDTO.setKeyword(keyword);

        System.out.println(searchService.searchKeyword(searchKeywordDTO));
    }
}
