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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping
    public ResponseEntity<?> getTourInfos(@RequestBody SearchTourInfoDTO searchTourInfoDTO) {
        try {
            return new ResponseEntity<>(searchService.searchTourInfo(setSearchDTO(searchTourInfoDTO)), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getTourInfos : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 검색 내용 세팅
    private SearchTourInfoDTO setSearchDTO(SearchTourInfoDTO searchTourInfoDTO) throws Exception {
        int page = searchTourInfoDTO.getPage();
        int size = searchTourInfoDTO.getSize() > 0 ? searchTourInfoDTO.getSize() : 10;
        // 정렬 기준 : 1. 등록일순, 2. 제목순 - default = 1
        int type = searchTourInfoDTO.getType() != 1 || searchTourInfoDTO.getType() != 2 ? 1 : searchTourInfoDTO.getType();
        // 정렬 : 1. 내림차순 (DESC), 2. 오름차순 (ASC) - default = 1
        int sort = searchTourInfoDTO.getSort() != 1 || searchTourInfoDTO.getSort() != 2 ? 1 : searchTourInfoDTO.getSort();
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

        searchTourInfoDTO.setPage(page);
        searchTourInfoDTO.setSize(size);
        searchTourInfoDTO.setType(type);
        searchTourInfoDTO.setSort(sort);
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
