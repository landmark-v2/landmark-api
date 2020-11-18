package com.landmark.app.service.support.Impl;

import com.landmark.app.model.domain.support.Faq;
import com.landmark.app.model.dto.support.FaqDTO;
import com.landmark.app.model.repository.support.FaqRepository;
import com.landmark.app.service.support.FaqService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.landmark.app.utils.constants.Constants.ROLE_DEV;

@Service
public class FaqServiceImpl extends LoggerUtils implements FaqService {

    private FaqRepository faqRepository;

    public FaqServiceImpl(FaqRepository faqRepository) { this.faqRepository = faqRepository; }


    @Override
    public FaqDTO registerFaq(FaqDTO faqDTO, String role) {
        Faq faq = new Faq();
        faq.setTitle(faqDTO.getTitle());
        faq.setContent(faqDTO.getContent());
        faq.setCreatedTime(new Date());
        faq.setModifiedTime(new Date());
        return FaqDTO.of(faqRepository.saveAndFlush(faq));
    }

    @Override
    public FaqDTO updateFaq(FaqDTO.UpdateFaqDTO faqDTO, String role) {
        try{
            Faq faq = faqRepository.findById(faqDTO.getId());

            if(role.equalsIgnoreCase(ROLE_DEV)) {

                if(!StringUtils.isEmpty(faqDTO.getTitle())) {
                    faq.setTitle(faqDTO.getTitle());
                }

                if(!StringUtils.isEmpty(faqDTO.getContent())) {
                    faq.setContent(faqDTO.getContent());
                }
            }

            faq.setModifiedTime(new Date());

            return FaqDTO.of(faqRepository.save(faq));
        } catch (Exception e){
            logger.error("Faq update : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteFaq(int id, String role) {
        try{
            if(role.equalsIgnoreCase(ROLE_DEV)){
                faqRepository.deleteById(id);
                return true;
            }
        } catch (Exception e){
            logger.error("Faq delete : " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<FaqDTO> getAllFaq() {
        try{
            List<FaqDTO> faqs = FaqDTO.of(faqRepository.findAll());
            return faqs;
        } catch (Exception e){
            logger.error("findAll : " + e.getMessage());
            return null;
        }
    }

    @Override
    public FaqDTO getFaq(int id) {
        try{
            return FaqDTO.of(faqRepository.findById(id));
        } catch (Exception e){
            logger.error("getFaq : " + e.getMessage());
            return null;
        }
    }


}
