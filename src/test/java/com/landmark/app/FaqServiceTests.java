package com.landmark.app;

import com.landmark.app.model.dto.support.FaqDTO;
import com.landmark.app.service.support.FaqService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FaqServiceTests {
    private FaqService faqService;

    @Autowired
    public FaqServiceTests(FaqService faqService) { this.faqService = faqService; }

    @Test
    public void registerFaqTest(){
        try{
            FaqDTO faqDTO = new FaqDTO();
            faqDTO.setTitle("faq test");
            faqDTO.setContent("테스트입니다.");
            System.out.println(faqService.registerFaq(faqDTO));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateFaqTest(){
        try{
            FaqDTO faqDTO = new FaqDTO();
            faqDTO.setId(2);
            faqDTO.setTitle("테스트");
            faqDTO.setContent("업데이트");
            faqService.updateFaq(faqDTO);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteFaqTest(){
        try{
            faqService.deleteFaq(2);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
