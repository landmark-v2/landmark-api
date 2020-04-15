package com.landmark.app;

import com.landmark.app.service.TourReviewService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TourReviewServiceTests {

    private TourReviewService tourHistoryService;

    @Autowired
    public TourReviewServiceTests(TourReviewService tourHistoryService) {
        this.tourHistoryService = tourHistoryService;
    }

    @Test
    @Ignore
    public void getRecentHistoriesTest() {
        int userId = 2;
        System.out.println(tourHistoryService.getRecentHistories(userId));
    }

}
