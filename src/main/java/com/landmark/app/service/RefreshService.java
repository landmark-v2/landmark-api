package com.landmark.app.service;

import com.landmark.app.model.domain.*;
import com.landmark.app.model.repository.*;
import com.landmark.app.utils.constants.Constants;
import com.landmark.app.utils.LoggerUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        refreshAreaCode();
        refreshSigunguCode();
        refreshContentTypeId();
        refreshCat1();
        refreshCat2();
        refreshCat3();

        logger.info("-- refresh --");
    }

    public void refreshAreaCode() {
        try {
            List<AreaCode> areaCodes = areaCodeRepository.findAll();

            for (AreaCode areaCode : areaCodes) {
                JSONObject areaCodeJson = new JSONObject();
                areaCodeJson.put("code", areaCode.getCode());
                areaCodeJson.put("name", areaCode.getName());
                Constants.areaCodes.add(areaCodeJson);
            }

            logger.info("AreaCode count = " + Constants.areaCodes.size());
        } catch (Exception e) {
            logger.error("refreshAreaCode : " + e.getMessage());
        }
    }

    public void refreshSigunguCode() {
        try {
            List<SigunguCode> sigunguCodes = sigunguCodeRepository.findAll();

            for (SigunguCode sigunguCode : sigunguCodes) {
                JSONObject sigunguCodeJson = new JSONObject();
                sigunguCodeJson.put("areaCode", sigunguCode.getAreaCode());
                sigunguCodeJson.put("code", sigunguCode.getCode());
                sigunguCodeJson.put("name", sigunguCode.getName());
                Constants.sigunguCodes.add(sigunguCodeJson);
            }

            logger.info("SigunguCode count = " + Constants.sigunguCodes.size());
        } catch (Exception e) {
            logger.error("refreshSigunguCode : " + e.getMessage());
        }
    }

    public void refreshContentTypeId() {
        try {
            List<ContentType> contentTypes = contentTypeRepository.findAll();

            for (ContentType contentType : contentTypes) {
                JSONObject contentTypeIdJson = new JSONObject();
                contentTypeIdJson.put("contentTypeId", contentType.getContentTypeId());
                contentTypeIdJson.put("name", contentType.getName());
                Constants.contentTypeIds.add(contentTypeIdJson);
            }

            logger.info("ContentTypeId count = " + Constants.contentTypeIds.size());
        } catch (Exception e) {
            logger.error("refreshContentTypeId : " + e.getMessage());
        }
    }

    public void refreshCat1() {
        try {
            List<Cat1> cat1s = cat1Repository.findAll();

            for (Cat1 cat1 : cat1s) {
                JSONObject cat1Json = new JSONObject();
                cat1Json.put("code", cat1.getCode());
                cat1Json.put("name", cat1.getName());
                Constants.cat1s.add(cat1Json);
            }

            logger.info("Cat1 count = " + Constants.cat1s.size());
        } catch (Exception e) {
            logger.error("refreshCat1 : " + e.getMessage());
        }
    }

    public void refreshCat2() {
        try {
            List<Cat2> cat2s = cat2Repository.findAll();

            for (Cat2 cat2 : cat2s) {
                JSONObject cat2Json = new JSONObject();
                cat2Json.put("code", cat2.getCode());
                cat2Json.put("name", cat2.getName());
                Constants.cat2s.add(cat2Json);
            }

            logger.info("Cat2 count = " + Constants.cat2s.size());
        } catch (Exception e) {
            logger.error("refreshCat2 : " + e.getMessage());
        }
    }

    public void refreshCat3() {
        try {
            List<Cat3> cat3s = cat3Repository.findAll();

            for (Cat3 cat3 : cat3s) {
                JSONObject cat3Json = new JSONObject();
                cat3Json.put("code", cat3.getCode());
                cat3Json.put("name", cat3.getName());
                Constants.cat3s.add(cat3Json);
            }

            logger.info("Cat3 count = " + Constants.cat3s.size());
        } catch (Exception e) {
            logger.error("refreshCat3 : " + e.getMessage());
        }
    }
}
