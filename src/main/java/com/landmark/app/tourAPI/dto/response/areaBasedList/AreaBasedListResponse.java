package com.landmark.app.tourAPI.dto.response.areaBasedList;

import com.landmark.app.tourAPI.dto.Header;
import com.landmark.app.tourAPI.dto.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class AreaBasedListResponse {

    private Header header;
    private List<AreaBasedList> areaBasedList;
    private PageInfo pageInfo;

}
