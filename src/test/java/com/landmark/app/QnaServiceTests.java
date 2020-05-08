package com.landmark.app;

import com.landmark.app.model.domain.support.QnaComment;
import com.landmark.app.model.dto.support.QnaCommentDTO;
import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.service.support.QnaService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QnaServiceTests {

    private QnaService qnaService;

    @Autowired
    public QnaServiceTests(QnaService qnaService) { this.qnaService = qnaService; }

    @Test
    public void registerQnaTest(){
        try{
            QnaDTO qnaDTO = new QnaDTO();
            qnaDTO.setUserId(10);
            qnaDTO.setTitle("qna남겨요");
            qnaDTO.setContent("qna창이 이상해요");
            System.out.println(qnaService.registerQna(qnaDTO).toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void deleteQnaTest(){
        try{
            int id = 1;
            int userId = 10;
            qnaService.deleteQna(id, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateQnaTest(){
        try{
            int id = 2;
            QnaDTO qnaDTO = new QnaDTO();
            qnaDTO.setId(2);
            qnaDTO.setUserId(10);
            qnaDTO.setTitle("qna ok");
            qnaDTO.setContent("qna가 정상적으로 작동합니다.");
            qnaService.updateQna(qnaDTO, id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void registerCommentTest(){
        try{
            QnaCommentDTO commentDTO = new QnaCommentDTO();
            commentDTO.setUserId(2);
            commentDTO.setComment("comment check");
            qnaService.registerQnaComment(commentDTO, 2);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateCommentTest(){
        try{
            QnaCommentDTO commentDTO = new QnaCommentDTO();
            commentDTO.setId(2);
            commentDTO.setUserId(2);
            commentDTO.setComment("코멘트 수정");
            qnaService.updateQnaComment(commentDTO, 2);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCommentTest(){
        try{
            qnaService.deleteQnaComment(2, 2, 0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
