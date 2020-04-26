package com.landmark.app.utils;

import com.landmark.app.model.dto.SearchTourInfoDTO;

import static com.landmark.app.utils.constants.SearchType.*;

public class SearchTypeHelper {

    public static int searchType(SearchTourInfoDTO searchDTO) {
        int searchType = 0;

        if (!searchDTO.getKeyword().equals("")) {
            if (searchDTO.getAreaCode() != 0) {
                if (!searchDTO.getCat1().equals("")) {
                    if (searchDTO.getSigunguCode() == 0) {
                        searchType = SEARCH_TYPE_AREA_CAT1_KEYWORD;

                        if (!searchDTO.getCat2().equals("")) {
                            searchType = SEARCH_TYPE_AREA_CAT1_CAT2_KEYWORD;

                            if (!searchDTO.getCat3().equals("")) {
                                searchType = SEARCH_TYPE_AREA_CAT1_CAT2_CAT3_KEYWORD;
                            }
                        }
                    } else {
                        searchType = SEARCH_TYPE_AREA_SIGUNGU_CAT1_KEYWORD;

                        if (!searchDTO.getCat2().equals("")) {
                            searchType = SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_KEYWORD;

                            if (!searchDTO.getCat3().equals("")) {
                                searchType = SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_CAT3_KEYWORD;
                            }
                        }
                    }
                } else {
                    if (searchDTO.getSigunguCode() == 0) {
                        searchType = SEARCH_TYPE_AREA_KEYWORD;
                    } else {
                        searchType = SEARCH_TYPE_AREA_SIGUNGU_KEYWORD;
                    }
                }
            } else {
                if (!searchDTO.getCat1().equals("")) {
                    searchType = SEARCH_TYPE_CAT1_KEYWORD;

                    if (!searchDTO.getCat2().equals("")) {
                        searchType = SEARCH_TYPE_CAT1_CAT2_KEYWORD;

                        if (!searchDTO.getCat3().equals("")) {
                            searchType = SEARCH_TYPE_CAT1_CAT2_CAT3_KEYWORD;
                        }
                    }
                } else {
                    searchType = SEARCH_TYPE_KEYWORD;
                }
            }
        } else {
            if (searchDTO.getAreaCode() != 0) {
                if (!searchDTO.getCat1().equals("")) {
                    if (searchDTO.getSigunguCode() == 0) {
                        searchType = SEARCH_TYPE_AREA_CAT1;

                        if (!searchDTO.getCat2().equals("")) {
                            searchType = SEARCH_TYPE_AREA_CAT1_CAT2;

                            if (!searchDTO.getCat3().equals("")) {
                                searchType = SEARCH_TYPE_AREA_CAT1_CAT2_CAT3;
                            }
                        }
                    } else {
                        searchType = SEARCH_TYPE_AREA_SIGUNGU_CAT1;

                        if (!searchDTO.getCat2().equals("")) {
                            searchType = SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2;

                            if (!searchDTO.getCat3().equals("")) {
                                searchType = SEARCH_TYPE_AREA_SIGUNGU_CAT1_CAT2_CAT3;
                            }
                        }
                    }
                } else {
                    if (searchDTO.getSigunguCode() == 0) {
                        searchType = SEARCH_TYPE_AREA;
                    } else {
                        searchType = SEARCH_TYPE_AREA_SIGUNGU;
                    }
                }
            } else {
                if (!searchDTO.getCat1().equals("")) {
                    searchType = SEARCH_TYPE_CAT1;

                    if (!searchDTO.getCat2().equals("")) {
                        searchType = SEARCH_TYPE_CAT1_CAT2;

                        if (!searchDTO.getCat3().equals("")) {
                            searchType = SEARCH_TYPE_CAT1_CAT2_CAT3;
                        }
                    }
                } else {
                    searchType = SEARCH_TYPE_CONTENT_TYPE;
                }
            }
        }

        return searchType;
    }

}
