package com.landmark.app;

import com.landmark.app.model.dto.support.NoticeDTO;
import com.landmark.app.service.support.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NoticeServiceTests {

    private NoticeService noticeService;

    @Autowired
    public NoticeServiceTests(NoticeService noticeService) { this.noticeService = noticeService; }

    @Test
    public void registerNoticeTest(){
        try{
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setTitle("공지");
            noticeDTO.setContent("공지공지");
            System.out.println(noticeService.registerNotice(noticeDTO));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateNoticeTest(){
        try{
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setId(1);
            noticeDTO.setTitle("공지 수정");
            noticeDTO.setContent("공지 수정합니다.");
            noticeService.updateNotice(noticeDTO);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteNoticeTest(){
        try{
            noticeService.deleteNotice(1);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
