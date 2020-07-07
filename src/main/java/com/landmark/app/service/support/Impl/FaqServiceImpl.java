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
            Faq faq = new Faq();
            faq.setTitle(faqDTO.getTitle());
            faq.setContent(faqDTO.getContent());
            faq.setCreatedTime(faqDTO.getCreatedTime());
            faq.setModifiedTime(new Date());
            return FaqDTO.of(faqRepository.saveAndFlush(faq));
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
