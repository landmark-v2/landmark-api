package com.landmark.app.tourAPI.dto.response.searchFestival;

import com.landmark.app.tourAPI.dto.Header;
import com.landmark.app.tourAPI.dto.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class SearchFestivalResponse {

    private Header header;
    private List<SearchFestival> searchFestival;
    private PageInfo pageInfo;

}
