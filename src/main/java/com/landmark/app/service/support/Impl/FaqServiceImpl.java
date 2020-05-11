package com.landmark.app.service.support.Impl;

import com.landmark.app.model.domain.support.Faq;
import com.landmark.app.model.dto.support.FaqDTO;
import com.landmark.app.model.repository.support.FaqRepository;
import com.landmark.app.service.support.FaqService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FaqServiceImpl extends LoggerUtils implements FaqService {

    private FaqRepository faqRepository;

    public FaqServiceImpl(FaqRepository faqRepository) { this.faqRepository = faqRepository; }

    public FaqDTO save(FaqDTO faqDTO){
        try{
            faqDTO.setModifiedTime(new Date());
            Faq faq = faqRepository.saveAndFlush(Faq.of(faqDTO));
            return FaqDTO.of(faq);
        } catch (Exception e){
            logger.error("Faq save : " + e.getMessage());
            return null;
        }
    }


    @Override
    public FaqDTO registerFaq(FaqDTO faqDTO) {
        faqDTO.setCreatedTime(new Date());
        return save(faqDTO);
    }

    @Override
    public void updateFaq(FaqDTO faqDTO) {
        try{
            save(faqDTO);
        } catch (Exception e){
            logger.error("Faq update : " + e.getMessage());
        }
    }

    @Override
    public void deleteFaq(int id) {
        try{
            faqRepository.deleteById(id);
        } catch (Exception e){
            logger.error("Faq delete : " + e.getMessage());
        }

    }
}
