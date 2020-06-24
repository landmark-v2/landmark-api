package com.landmark.app.service.impl;

import com.landmark.app.model.dto.SearchTourInfoDTO;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.service.SearchService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.SearchTypeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.landmark.app.utils.constants.SearchType.*;

@Service
public class SearchServiceImpl extends LoggerUtils implements SearchService {

    private TourInfoRepository tourInfoRepository;

    @Autowired
    public SearchServiceImpl(TourInfoRepository tourInfoRepository) {
        this.tourInfoRepository = tourInfoRepository;
    }

    @Override
    public List<TourInfoDTO> searchTourInfo(SearchTourInfoDTO searchDTO) throws Exception {
        int searchType = SearchTypeHelper.searchType(searchDTO);

        try {
            if (searchType == SEARCH_TYPE_CONTENT_TYPE) {
                logger.info("search tourInfo : contentTypeId");
                return TourInfoDTO.of(tourInfoRepository.findAllByContentTypeIdOrderByCreatedTimeDesc(searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_AREA) {
                logger.info("search tourInfo : contentTypeId, areaCode");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeOrderByCreatedTimeDesc(searchDTO.getAreaCode()));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, cat2");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_CAT3) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, cat2, cat3");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndCat3AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_CAT2) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, cat2");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndCat2AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_CAT2_CAT3) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, cat2, cat3");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndCat2AndCat3AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_CAT1) {
                logger.info("search tourInfo : contentTypeId, cat1");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getCat1(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_CAT1_CAT2) {
                logger.info("search tourInfo : contentTypeId, cat1, cat2");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndCat2AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_CAT1_CAT2_CAT3) {
                logger.info("search tourInfo : contentTypeId, cat1, cat2, cat3");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndCat2AndCat3AndContentTypeIdOrderByCreatedTimeDesc(searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId()));
            } else if (searchType == SEARCH_TYPE_AREA_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, cat2, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_CAT3_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, cat2, cat3, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndCat3AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_CAT2_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, cat2, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndCat2AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_CAT2_CAT3_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, cat2, cat3, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndCat2AndCat3AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_CAT1_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, cat1, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getCat1(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_CAT1_CAT2_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, cat1, cat2, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndCat2AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_CAT1_CAT2_CAT3_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, cat1, cat2, cat3, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndCat2AndCat3AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else if (searchType == SEARCH_TYPE_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(searchDTO.getContentTypeId(), searchDTO.getKeyword()));
            } else {
                logger.info("search tourInfo : all");
                return TourInfoDTO.of(tourInfoRepository.findAll());
            }
        } catch (Exception e) {
            logger.error("searchTourInfo : " + e.getMessage());
            return null;
        }
    }

}
