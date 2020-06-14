package com.landmark.app.controller;

import com.landmark.app.model.dto.SearchTourInfoDTO;
import com.landmark.app.service.SearchService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.constants.Constants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.landmark.app.utils.constants.Constants.*;

@RestController
@RequestMapping(value = SEARCH_API)
public class SearchController extends LoggerUtils {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 관광지 정보 검색 (필터링)
     */
    @PostMapping
    public ResponseEntity<?> getTourInfos(@RequestBody SearchTourInfoDTO searchTourInfoDTO) {
        try {
            System.out.println(searchTourInfoDTO.toString());
            return new ResponseEntity<>(searchService.searchTourInfo(setSearchDTO(searchTourInfoDTO)), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getTourInfos : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 검색 내용 세팅
    private SearchTourInfoDTO setSearchDTO(SearchTourInfoDTO searchTourInfoDTO) throws Exception {
        int areaCode = searchTourInfoDTO.getAreaCode();
        int sigunguCode = areaCode != 0 ? searchTourInfoDTO.getSigunguCode() : 0;

        int contentTypeId = searchTourInfoDTO.getContentTypeId();

        if (!checkContentTypeId(contentTypeId)) {
            contentTypeId = 0;
        }

        String cat1 = StringUtils.isEmpty(searchTourInfoDTO.getCat1()) ? "" : searchTourInfoDTO.getCat1();
        String cat2 = "";
        String cat3 = "";

        if (!cat1.equals("")) {
            cat2 = StringUtils.isEmpty(searchTourInfoDTO.getCat2()) ? "" : searchTourInfoDTO.getCat2();

            if (!cat2.equals("")) {
                cat3 = StringUtils.isEmpty(searchTourInfoDTO.getCat3()) ? "" : searchTourInfoDTO.getCat3();
            }
        }

        String keyword = StringUtils.isEmpty(searchTourInfoDTO.getKeyword()) ? "" : searchTourInfoDTO.getKeyword();

        searchTourInfoDTO.setAreaCode(areaCode);
        searchTourInfoDTO.setSigunguCode(sigunguCode);
        searchTourInfoDTO.setContentTypeId(contentTypeId);
        searchTourInfoDTO.setCat1(cat1);
        searchTourInfoDTO.setCat2(cat2);
        searchTourInfoDTO.setCat3(cat3);
        searchTourInfoDTO.setKeyword(keyword);

        return searchTourInfoDTO;
    }

    // contentTypeId 가 디비에 저장된 데이터인지 체크
    private boolean checkContentTypeId(int contentTypeId) throws Exception {
        if (!contentTypeIds.isEmpty()) {
            List<Integer> contentTypeIdList = new ArrayList<>();

            for (Object obj : contentTypeIds) {
                JSONObject contentTypeIdJson = (JSONObject) obj;
                int contentType = Integer.parseInt(contentTypeIdJson.get("contentTypeId").toString());
                contentTypeIdList.add(contentType);
            }

            if (contentTypeIdList.contains(contentTypeId)) {
                return true;
            }
        }

        return false;
    }

}
