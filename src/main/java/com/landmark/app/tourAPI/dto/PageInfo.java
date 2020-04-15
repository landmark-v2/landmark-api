package com.landmark.app.tourAPI.dto;

import lombok.Data;

@Data
public class PageInfo {

    private int numOfRows;      // 한 페이지 결과 수 (size)
    private int pageNo;         // 페이지 번호 (page)
    private int totalCount;     // 전체 결과 수

}
