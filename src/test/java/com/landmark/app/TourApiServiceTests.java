package com.landmark.app;

import com.landmark.app.tourAPI.TourApiService;
import com.landmark.app.tourAPI.dto.request.TourApiRequest;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TourApiServiceTests {

    private TourApiService tourApiService;

    @Autowired
    public TourApiServiceTests(TourApiService tourApiService) {
        this.tourApiService = tourApiService;
    }

    @Test
    @Ignore
    public void areaBasedListTest() {
        TourApiRequest tourApiRequest = new TourApiRequest();
        System.out.println(tourApiService.areaBasedList(1, 2, tourApiRequest));
    }

    @Test
    @Ignore
    public void searchKeywordTest() {
        TourApiRequest tourApiRequest = new TourApiRequest();
        tourApiRequest.setKeyword("강원도");
        System.out.println(tourApiService.searchKeyword(1, 2, tourApiRequest));
    }

    @Test
    @Ignore
    public void searchFestivalTest() {
        TourApiRequest tourApiRequest = new TourApiRequest();
        tourApiRequest.setEventStartDate(20200101);
        System.out.println(tourApiService.searchFestival(1, 2, tourApiRequest));
    }

    @Test
    @Ignore
    public void detailIntroTest() {
        TourApiRequest tourApiRequest = new TourApiRequest();
        tourApiRequest.setContentId(1054221);
        tourApiRequest.setContentTypeId(14);
        System.out.println(tourApiService.detailIntro(1, 2, tourApiRequest));
    }

}
