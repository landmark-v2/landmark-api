package com.landmark.app.service;

import com.landmark.app.model.domain.*;
import com.landmark.app.model.repository.*;
import com.landmark.app.utils.constants.Constants;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RefreshService extends LoggerUtils {

    private AreaCodeRepository areaCodeRepository;
    private SigunguCodeRepository sigunguCodeRepository;
    private ContentTypeRepository contentTypeRepository;
    private Cat1Repository cat1Repository;
    private Cat2Repository cat2Repository;
    private Cat3Repository cat3Repository;

    @Autowired
    public RefreshService(AreaCodeRepository areaCodeRepository, SigunguCodeRepository sigunguCodeRepository, ContentTypeRepository contentTypeRepository,
                          Cat1Repository cat1Repository, Cat2Repository cat2Repository, Cat3Repository cat3Repository) {
        this.areaCodeRepository = areaCodeRepository;
        this.sigunguCodeRepository = sigunguCodeRepository;
        this.contentTypeRepository = contentTypeRepository;
        this.cat1Repository = cat1Repository;
        this.cat2Repository = cat2Repository;
        this.cat3Repository = cat3Repository;
    }

    public void refreshAll() {
        refreshAreaCodeMap();
        refreshSigunguCodeMap();
        refreshContentTypeMap();
        refreshCat1Map();
        refreshCat2Map();
        refreshCat3Map();
    }

    public void refreshAreaCodeMap() {
        try {
            List<AreaCode> areaCodes = areaCodeRepository.findAll();

            for (AreaCode areaCode : areaCodes) {
                Constants.areaCodeMap.put(areaCode.getCode(), areaCode.getName());
            }
        } catch (Exception e) {
            logger.error("refreshAreaCodeMap : " + e.getMessage());
        }
    }

    public void refreshSigunguCodeMap() {
        try {
            List<SigunguCode> sigunguCodes = sigunguCodeRepository.findAll();

            for (SigunguCode sigunguCode : sigunguCodes) {
                Map<Integer, Integer> pkMap = new HashMap<>();
                pkMap.put(sigunguCode.getAreaCode(), sigunguCode.getCode());
                Constants.sigunguCodeMap.put(pkMap, sigunguCode.getName());
            }

            logger.info("-- refresh sigunguCodeMap --");
        } catch (Exception e) {
            logger.error("refreshSigunguCodeMap : " + e.getMessage());
        }
    }

    public void refreshContentTypeMap() {
        try {
            List<ContentType> contentTypes = contentTypeRepository.findAll();

            for (ContentType contentType : contentTypes) {
                Constants.contentTypeMap.put(contentType.getContentTypeId(), contentType.getName());
            }

            logger.info("-- refresh contentTypeMap --");
        } catch (Exception e) {
            logger.error("refreshContentTypeMap : " + e.getMessage());
        }
    }

    public void refreshCat1Map() {
        try {
            List<Cat1> cat1s = cat1Repository.findAll();

            for (Cat1 cat1 : cat1s) {
                Constants.cat1Map.put(cat1.getCode(), cat1.getName());
            }

            logger.info("-- refresh cat1Map --");
        } catch (Exception e) {
            logger.error("refreshCat1Map : " + e.getMessage());
        }
    }

    public void refreshCat2Map() {
        try {
            List<Cat2> cat2s = cat2Repository.findAll();

            for (Cat2 cat2 : cat2s) {
                Constants.cat2Map.put(cat2.getCode(), cat2.getName());
            }

            logger.info("-- refresh cat2Map --");
        } catch (Exception e) {
            logger.error("refreshCat2Map : " + e.getMessage());
        }
    }

    public void refreshCat3Map() {
        try {
            List<Cat3> cat3s = cat3Repository.findAll();

            for (Cat3 cat3 : cat3s) {
                Constants.cat3Map.put(cat3.getCode(), cat3.getName());
            }

            logger.info("-- refresh cat3Map --");
        } catch (Exception e) {
            logger.error("refreshCat3Map : " + e.getMessage());
        }
    }
}
