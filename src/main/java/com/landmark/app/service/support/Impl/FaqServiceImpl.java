package com.landmark.app.service.support.Impl;

import com.landmark.app.model.domain.support.Faq;
import com.landmark.app.model.dto.support.FaqDTO;
import com.landmark.app.model.repository.support.FaqRepository;
import com.landmark.app.service.support.FaqService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.landmark.app.utils.constants.Constants.ROLE_DEV;

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
    public FaqDTO updateFaq(FaqDTO faqDTO, String role) {
        try{
            if(role.equalsIgnoreCase(ROLE_DEV)) {
                return save(faqDTO);
            }
        } catch (Exception e){
            logger.error("Faq update : " + e.getMessage());
        }
        return null;
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

    /*
    private FaqDTO getFaq(int id, String title, String content, Date modifiedTime){
        FaqDTO faq = new FaqDTO();
        faq.setId(id);
        faq.setTitle(title);
        faq.setContent(content);
        faq.setModifiedTime(modifiedTime);
        return faq;
    }
     */
}
