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
    private String keyword;         // 요청 키워드
    private int eventStartDate;     // 행사 시작일
    private int eventEndDate;       // 행사 종료일
    private int contentId;          // 콘텐츠 id
    private String defaultYn;

    @Override
    public String toString() {
        String params = defaultInfo.toString();

        if (listYN != null && !listYN.equals("")) {
            params += "&listYN=" + listYN;
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

        if (keyword != null && !keyword.equals("")) {
            params += "&keyword=" + StaticHelper.encodeUTF8(keyword);
        }

        if (eventStartDate != 0) {
            params += "&eventStartDate=" + eventStartDate;
        }

        if (eventEndDate != 0) {
            params += "&eventEndDate=" + eventEndDate;
        }

        if (contentId != 0) {
            params += "&contentId=" + contentId;
        }

        if (defaultYn != null && !defaultYn.equals("")) {
            params += "&defaultYN=" + defaultYn;
        }

        return params;
    }
}
