package com.landmark.app.service.impl;

import com.landmark.app.model.dto.SearchTourInfoDTO;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.service.SearchService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.SearchTypeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import static com.landmark.app.utils.constants.SearchType.*;

@Service
public class SearchServiceImpl extends LoggerUtils implements SearchService {

    private TourInfoRepository tourInfoRepository;

    @Autowired
    public SearchServiceImpl(TourInfoRepository tourInfoRepository) {
        this.tourInfoRepository = tourInfoRepository;
    }

    @Override
    public Page<TourInfoDTO> searchTourInfo(SearchTourInfoDTO searchDTO) throws Exception {
        String type = searchDTO.getType() == 1 ? "createdTime" : "title";

        Pageable pageable = null;

        if (searchDTO.getSort() == 1) {
            pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(type).descending());
        } else {
            pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(type).ascending());
        }

        int searchType = searchDTO.getContentTypeId() != 0 ? SearchTypeHelper.searchType(searchDTO) : 0;

        try {
            if (searchType == SEARCH_TYPE_CONTENT_TYPE) {
                logger.info("search tourInfo : contentTypeId");
                return TourInfoDTO.of(tourInfoRepository.findAllByContentTypeId(searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA) {
                logger.info("search tourInfo : contentTypeId, areaCode");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndContentTypeId(searchDTO.getAreaCode(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndContentTypeId(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndContentTypeId(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, cat2");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndContentTypeId(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_CAT3) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, cat2, cat3");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndCat3AndContentTypeId(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndContentTypeId(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_CAT2) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, cat2");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndCat2AndContentTypeId(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_CAT2_CAT3) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, cat2, cat3");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndCat2AndCat3AndContentTypeId(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_CAT1) {
                logger.info("search tourInfo : contentTypeId, cat1");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndContentTypeId(searchDTO.getCat1(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_CAT1_CAT2) {
                logger.info("search tourInfo : contentTypeId, cat1, cat2");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndCat2AndContentTypeId(searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_CAT1_CAT2_CAT3) {
                logger.info("search tourInfo : contentTypeId, cat1, cat2, cat3");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndCat2AndCat3AndContentTypeId(searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndContentTypeIdAndTitleContaining(searchDTO.getAreaCode(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndContentTypeIdAndTitleContaining(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndContentTypeIdAndTitleContaining(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, cat2, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndContentTypeIdAndTitleContaining(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_CAT3_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, sigunguCode, cat1, cat2, cat3, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndCat3AndContentTypeIdAndTitleContaining(searchDTO.getAreaCode(), searchDTO.getSigunguCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndContentTypeIdAndTitleContaining(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_CAT2_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, cat2, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndCat2AndContentTypeIdAndTitleContaining(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_AREA_CAT1_CAT2_CAT3_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, areaCode, cat1, cat2, cat3, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAreaCodeAndCat1AndCat2AndCat3AndContentTypeIdAndTitleContaining(searchDTO.getAreaCode(), searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_CAT1_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, cat1, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndContentTypeIdAndTitleContaining(searchDTO.getCat1(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_CAT1_CAT2_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, cat1, cat2, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndCat2AndContentTypeIdAndTitleContaining(searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_CAT1_CAT2_CAT3_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, cat1, cat2, cat3, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByCat1AndCat2AndCat3AndContentTypeIdAndTitleContaining(searchDTO.getCat1(), searchDTO.getCat2(), searchDTO.getCat3(), searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else if (searchType == SEARCH_TYPE_KEYWORD) {
                logger.info("search tourInfo : contentTypeId, keyword");
                return TourInfoDTO.of(tourInfoRepository.findAllByAndContentTypeIdAndTitleContaining(searchDTO.getContentTypeId(), searchDTO.getKeyword(), pageable));
            } else {
                logger.info("search tourInfo : all");
                return TourInfoDTO.of(tourInfoRepository.findAll(pageable));
            }
        } catch (Exception e) {
            logger.error("searchTourInfo : " + e.getMessage());
            return null;
        }
    }

}
