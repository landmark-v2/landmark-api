package com.landmark.app.tourAPI.dto.request;

import com.landmark.app.utils.StaticHelper;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TourApiRequest {

    private DefaultInfo defaultInfo;
    private String listYN;          // 목록 구분
    private String arrange;         // 정렬 구분
    private int contentTypeId;      // 관광타입 id
    private int areaCode;           // 지역코드
    private int sigunguCode;        // 시군구코드
    private String cat1;            // 대분류
    private String cat2;            // 중분류
    private String cat3;            // 소분류
    private double mapX;            // X좌표
    private double mapY;            // Y좌표
    private int radius;             // 거리 반경
    private String keyword;         // 요청 키워드
    private int eventStartDate;     // 행사 시작일
    private int eventEndDate;       // 행사 종료일
    private int hanOk;              // 한옥 여부 (1 - T / 0 - F)
    private int benikia;            // 베니키아 여부 (1 - T / 0 - F)
    private int goodStay;           // 굿스테이 여부 (1 - T / 0 - F)
    private String defaultYN;       // 기본정보 조회
    private String firstImageYN;    // 대표이미지 조회
    private String areacodeYN;      // 지역코드 조회
    private String catcodeYN;       // 서비스분류코드 조회
    private String addrinfoYN;      // 주소 조회
    private String mapinfoYN;       // 좌표 조회
    private String overviewYN;      // 개요 조회
    private int contentId;          // 콘텐츠 id
    private String imageYN;         // 이미지 조회1
    private String subImageYN;      // 이미지 조회2

    @Override
    public String toString() {
        String params = defaultInfo.toString();

        if (listYN != null && !listYN.equals("")) {
            params += "&listYn=" + listYN;
        }

        if (arrange != null && !arrange.equals("")) {
            params += "&arrange=" + arrange;
        }

        if (contentTypeId != 0) {
            params += "&contentTypeId=" + contentTypeId;
        }

        if (areaCode != 0) {
            params += "&areaCode=" + areaCode;

            if (sigunguCode != 0) {
                params += "&sigunguCode=" + sigunguCode;
            }
        }

        if (cat1 != null && !cat1.equals("")) {
            params += "&cat1=" + cat1;

            if (cat2 != null && !cat2.equals("")) {
                params += "&cat2=" + cat2;

                if (cat3 != null && !cat3.equals("")) {
                    params += "&cat3=" + cat3;
                }
            }
        }

        if (mapX != 0) {
            params += "&mapX=" + mapX;
        }

        if (mapY != 0) {
            params += "&mapY=" + mapY;
        }

        if (radius != 0) {
            params += "&radius=" + radius;
        }

        if (keyword != null && !keyword.equals("")) {
            params += "&keyword=" + StaticHelper.encodeUTF8(keyword);
        }

        if (eventStartDate != 0) {
            params += "&eventStartDate=" + eventStartDate;
        }

        if (eventEndDate != 0) {
            params += "&eventEndDate=" + eventEndDate;
        }

        if (hanOk != 0) {
            params += "&hanOk=" + hanOk;
        }

        if (benikia != 0) {
            params += "&benikia=" + benikia;
        }

        if (goodStay != 0) {
            params += "&goodStay=" + goodStay;
        }

        if (contentId != 0) {
            params += "&contentId=" + contentId;
        }

        if (defaultYN != null && !defaultYN.equals("")) {
            params += "&defaultYN=" + defaultYN;
        }

        if (firstImageYN != null && !firstImageYN.equals("")) {
            params += "&firstImageYN=" + firstImageYN;
        }

        if (areacodeYN != null && !areacodeYN.equals("")) {
            params += "&areacodeYN=" + areacodeYN;
        }

        if (catcodeYN != null && !catcodeYN.equals("")) {
            params += "&catcodeYN=" + catcodeYN;
        }

        if (addrinfoYN != null && !addrinfoYN.equals("")) {
            params += "&addrinfoYN=" + addrinfoYN;
        }

        if (mapinfoYN != null && !mapinfoYN.equals("")) {
            params += "&mapinfoYN=" + mapinfoYN;
        }

        if (overviewYN != null && !overviewYN.equals("")) {
            params += "&overviewYN=" + overviewYN;
        }

        if (imageYN != null && !imageYN.equals("")) {
            params += "&imageYN=" + imageYN;
        }

        if (subImageYN != null && !subImageYN.equals("")) {
            params += "&subImageYN=" + subImageYN;
        }

        return params;
    }
}
