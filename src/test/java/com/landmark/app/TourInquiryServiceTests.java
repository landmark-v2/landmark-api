package com.landmark.app;

import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.service.TourInquiryService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TourInquiryServiceTests {

    private TourInquiryService tourInquiryService;

    @Autowired
    public TourInquiryServiceTests(TourInquiryService tourInquiryService) {
        this.tourInquiryService = tourInquiryService;
    }

    @Test
    @Ignore
    public void registerTourInfoTest() {
        try{
            TourInfoDTO tourInfoDTO = new TourInfoDTO();
            tourInfoDTO.setUserId(2);
            tourInfoDTO.setOverview("커요");
            tourInfoDTO.setTitle("강원대학교");
            System.out.println(tourInquiryService.registerTourist(tourInfoDTO).toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void deleteTourInfoTest(){
        try{
            int id = 25380;
            int userId = 2;
            tourInquiryService.deleteTours(id, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateTourInfoTest(){
        try{
            TourInfoDTO tourInfoDTO = new TourInfoDTO();
            tourInfoDTO.setId(25381);
            tourInfoDTO.setUserId(2);
            tourInfoDTO.setAreaCode(3532);
            tourInfoDTO.setOverview("컴퓨터과학전공");
            tourInfoDTO.setTitle("KNU");
            tourInquiryService.updateTours(tourInfoDTO);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
