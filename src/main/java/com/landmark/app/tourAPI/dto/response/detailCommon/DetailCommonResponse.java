package com.landmark.app.tourAPI.dto.response.detailCommon;

import com.landmark.app.tourAPI.dto.Header;
import com.landmark.app.tourAPI.dto.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class DetailCommonResponse {

    private Header header;
    private List<DetailCommon> detailCommon;
    private PageInfo pageInfo;

}