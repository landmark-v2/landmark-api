package com.landmark.app.tourAPI.dto.response.detailIntro;

import com.landmark.app.tourAPI.dto.Header;
import com.landmark.app.tourAPI.dto.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class DetailIntroResponse {

    private Header header;
    private List<DetailIntro> detailIntro;
    private PageInfo pageInfo;

}
