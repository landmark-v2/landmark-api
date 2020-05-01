package com.landmark.app;

import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.service.TourInfoService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TourInfoServiceTests {
    private com.landmark.app.service.TourInfoService TourInfoService;

    @Autowired
    public TourInfoServiceTests(TourInfoService TourInfoService) {
        this.TourInfoService = TourInfoService;
    }

    @Test
    @Ignore
    public void registerTourInfoTest() {
        try{
            TourInfoDTO tourInfoDTO = new TourInfoDTO();
            tourInfoDTO.setUserId(2);
            tourInfoDTO.setOverview("커요");
            tourInfoDTO.setTitle("강원대학교");
            System.out.println(TourInfoService.registerTourist(tourInfoDTO).toString());
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
            TourInfoService.deleteTours(id, userId);
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
            TourInfoService.updateTours(tourInfoDTO);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
