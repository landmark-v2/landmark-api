package com.landmark.app.tourAPI.dto.response.searchKeyword;

import com.landmark.app.tourAPI.dto.Header;
import com.landmark.app.tourAPI.dto.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class SearchKeywordResponse {

    private Header header;
    private List<SearchKeyword> searchKeyword;
    private PageInfo pageInfo;

}
