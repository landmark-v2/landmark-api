package com.landmark.app.controller;

import com.landmark.app.service.RefreshService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.landmark.app.utils.constants.Constants.CODE_API;

@RestController
@RequestMapping(value = CODE_API)
public class CodeController extends LoggerUtils {

    private RefreshService refreshService;

    @Autowired
    public CodeController(RefreshService refreshService) {
        this.refreshService = refreshService;
    }

    /**
     * 지역코드 전체 조회
     */
    @GetMapping(value = "/area")
    public ResponseEntity<?> getAreaCodes() {
        try {
            if (Constants.areaCodes.isEmpty()) {
                refreshService.refreshAreaCode();
            }

            logger.info("/code/area count = " + Constants.areaCodes.size());
            return new ResponseEntity<>(Constants.areaCodes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getAreaCodes : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 시군구코드 전체 조회
     */
    @GetMapping(value = "/sigungu")
    public ResponseEntity<?> getSigunguCodes() {
        try {
            if (Constants.sigunguCodes.isEmpty()) {
                refreshService.refreshSigunguCode();
            }

            logger.info("/code/sigungu count = " + Constants.sigunguCodes.size());
            return new ResponseEntity<>(Constants.sigunguCodes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getSigunguCodes : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 콘텐츠 타입 전체 조회
     */
    @GetMapping(value = "/content-type")
    public ResponseEntity<?> getContentTypeCodes() {
        try {
            if (Constants.contentTypeIds.isEmpty()) {
                refreshService.refreshContentTypeId();
            }

            logger.info("/code/content-type count = " + Constants.contentTypeIds.size());
            return new ResponseEntity<>(Constants.contentTypeIds, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getContentTypeCodes : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 대분류 전체 조회
     */
    @GetMapping(value = "/cat1")
    public ResponseEntity<?> getCat1Codes() {
        try {
            if (Constants.cat1s.isEmpty()) {
                refreshService.refreshCat1();
            }

            logger.info("/code/cat1 count = " + Constants.cat1s.size());
            return new ResponseEntity<>(Constants.cat1s, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getCat1Codes : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 중분류 전체 조회
     */
    @GetMapping(value = "/cat2")
    public ResponseEntity<?> getCat2Codes() {
        try {
            if (Constants.cat2s.isEmpty()) {
                refreshService.refreshCat2();
            }

            logger.info("/code/cat2 count = " + Constants.cat2s.size());
            return new ResponseEntity<>(Constants.cat2s, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getCat2Codes : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 소분류 전체 조회
     */
    @GetMapping(value = "/cat3")
    public ResponseEntity<?> getCat3Codes() {
        try {
            if (Constants.cat3s.isEmpty()) {
                refreshService.refreshCat3();
            }

            logger.info("/code/cat3 count = " + Constants.cat3s.size());
            return new ResponseEntity<>(Constants.cat3s, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getCat3Codes : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
