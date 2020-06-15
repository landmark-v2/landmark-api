package com.landmark.app.service.impl;

import com.landmark.app.model.domain.AreaCodeCount;
import com.landmark.app.model.domain.TourReview;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.model.repository.AreaCodeCountRepository;
import com.landmark.app.model.repository.TourReviewRepository;
import com.landmark.app.service.TourInfoService;
import com.landmark.app.service.TourReviewService;
import com.landmark.app.service.user.UserService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.StaticHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.landmark.app.utils.constants.Constants.*;

@Service
public class TourReviewServiceImpl extends LoggerUtils implements TourReviewService {

    private TourReviewRepository tourReviewRepository;
    private AreaCodeCountRepository areaCodeCountRepository;
    private TourInfoService tourInfoService;
    private UserService userService;

    @Autowired
    public TourReviewServiceImpl(TourReviewRepository tourReviewRepository, AreaCodeCountRepository areaCodeCountRepository, TourInfoService tourInfoService, UserService userService) {
        this.tourReviewRepository = tourReviewRepository;
        this.areaCodeCountRepository = areaCodeCountRepository;
        this.tourInfoService = tourInfoService;
        this.userService = userService;
    }

    @Override
    public TourReviewDTO save(TourReviewDTO tourReviewDTO) {
        try {
            TourReview tourReview = new TourReview();

            if (tourReviewDTO.getId() != 0) {
                tourReview.setId(tourReviewDTO.getId());
            }

            tourReview.setAreaCode(tourReviewDTO.getAreaCode());
            tourReview.setSigunguCode(tourReviewDTO.getSigunguCode());
            tourReview.setTitle(tourReviewDTO.getTitle());
            tourReview.setOverview(tourReviewDTO.getOverview());
            tourReview.setUserId(tourReviewDTO.getUserId());
            tourReview.setTourId(tourReviewDTO.getTourId());
            tourReview.setCreatedTime(StaticHelper.stringToDate(tourReviewDTO.getCreatedTime(), "yyyy-MM-dd HH:mm"));
            tourReview.setModifiedTime(new Date());
            tourReview.setFirstImage(tourReviewDTO.getFirstImage());
            tourReview.setPrivate(tourReviewDTO.isPrivate());
            return TourReviewDTO.of(tourReviewRepository.saveAndFlush(tourReview));
        } catch (Exception e) {
            logger.error("save : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TourReviewDTO.RecentReview> getRecentReviews(int userId) {
        List<TourReviewDTO.RecentReview> recentReviews = new ArrayList<>();

        try {
            List<TourReviewDTO> tourReviewDTOS = TourReviewDTO.of(tourReviewRepository.findTop10ByUserId(userId));

            for (TourReviewDTO tourReviewDTO : tourReviewDTOS) {
                String areaName = getAreaName(tourReviewDTO.getAreaCode());
                String sigunguName = getSigunguName(tourReviewDTO.getAreaCode(), tourReviewDTO.getSigunguCode());
                recentReviews.add(getRecentReview(tourReviewDTO.getId(), areaName, sigunguName, tourReviewDTO.getFirstImage(), tourReviewDTO.getModifiedTime()));
            }
        } catch (Exception e) {
            logger.error("getRecentHistories : " + e.getMessage());
        }

        return recentReviews;
    }

    private TourReviewDTO.RecentReview getRecentReview(int id, String areaName, String sigunguName, String firstImage, String modifiedTime) {
        TourReviewDTO.RecentReview recentReview = new TourReviewDTO.RecentReview();
        recentReview.setId(id);
        recentReview.setAreaName(areaName);
        recentReview.setSigunguName(sigunguName);
        recentReview.setFirstImage(firstImage);
        recentReview.setModifiedTime(modifiedTime);
        return recentReview;
    }

    private String getAreaName(int areaCode) throws Exception {
        if (!areaCodes.isEmpty()) {
            for (Object obj : areaCodes) {
                JSONObject areaCodeJson = (JSONObject) obj;

                if (Integer.parseInt(areaCodeJson.get("code").toString()) == areaCode) {
                    return areaCodeJson.get("name").toString();
                }
            }
        }

        return "";
    }

    private String getSigunguName(int areaCode, int sigunguCode) throws Exception {
        if (!sigunguCodes.isEmpty()) {
            for (Object obj : sigunguCodes) {
                JSONObject sigunguCodeJson = (JSONObject) obj;
                int area = Integer.parseInt(sigunguCodeJson.get("areaCode").toString());
                int sigungu = Integer.parseInt(sigunguCodeJson.get("code").toString());

                if (area == areaCode && sigungu == sigunguCode) {
                    return sigunguCodeJson.get("name").toString();
                }
            }
        }

        return "";
    }

    @Override
    public JSONArray countAllByUserIdGroupByAreaCode(int userId) {
        JSONArray jsonArr = new JSONArray();

        try {
            List<AreaCodeCount> counts = areaCodeCountRepository.countAllByUserIdGroupByAreaCode(userId);

            for (AreaCodeCount count : counts) {
                JSONObject countObj = new JSONObject();
                countObj.put("areaCode", count.getAreaCode());
                countObj.put("name", count.getName());
                countObj.put("count", count.getLevel());
                jsonArr.add(countObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("countAllByUserIdGroupByAreaCode : " + e.getMessage());
        }

        return jsonArr;
    }

    @Override
    public TourReviewDTO registerReview(TourReviewDTO tourReviewDTO) {
        tourReviewDTO.setCreatedTime(new Date());
        return save(tourReviewDTO);
    }

    @Override
    public List<TourReviewDTO> getReviewList(int userId, String roleName, TourReviewDTO.SearchReviewDTO searchReviewDTO) {
        String q = searchReviewDTO.getQ() != null ? searchReviewDTO.getQ() : "";
        Date startDate = searchReviewDTO.getStartDate();
        Date endDate = searchReviewDTO.getEndDate();

        if (roleName.equals(ROLE_USER)) {
            return allReviewListOfUser(userId, q, startDate, endDate);
        } else if (roleName.equals(ROLE_ADMIN)) {
            int tourId = searchReviewDTO.getTourId();
            return allReviewListOfAdmin(userId, tourId, q, startDate, endDate);
        } else if (roleName.equals(ROLE_DEV)) {
            int type = searchReviewDTO.getType() <= REVIEW_TYPE_TITLE ? searchReviewDTO.getType() : 0;
            return allReviewListOfDev(type, q, startDate, endDate);
        }

        return new ArrayList<>();
    }

    @Override
    public TourReviewDTO findById(int id) {
        try {
            TourReview tourReview = tourReviewRepository.findById(id);
            return TourReviewDTO.of(tourReview);
        } catch (Exception e) {
            logger.error("findById : " + e.getMessage());
            return null;
        }
    }

    @Override
    public TourReviewDTO updateReview(TourReviewDTO.UpdateReviewDTO updateReviewDTO) {
        try {
            TourReviewDTO tourReviewDTO = findById(updateReviewDTO.getId());

            if (!StringUtils.isEmpty(updateReviewDTO.getTitle())) {
                tourReviewDTO.setTitle(updateReviewDTO.getTitle());
            }

            if (!StringUtils.isEmpty(updateReviewDTO.getOverview())) {
                tourReviewDTO.setOverview(updateReviewDTO.getOverview());
            }

            if (updateReviewDTO.isPrivate() != tourReviewDTO.isPrivate()) {
                tourReviewDTO.setPrivate(updateReviewDTO.isPrivate());
            }

            System.out.println(updateReviewDTO);
            System.out.println(tourReviewDTO);

            return save(tourReviewDTO);
        } catch (Exception e) {
            logger.error("updateReview : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteReview(int userId, String role, int reviewId) {
        try {
            TourReviewDTO tourReviewDTO = findById(reviewId);

            if (role.equalsIgnoreCase(ROLE_ADMIN)) {
                TourInfoDTO tourInfoDTO = tourInfoService.findById(tourReviewDTO.getTourId());

                if (userId == tourInfoDTO.getUserId()) {
                    tourReviewRepository.deleteById(reviewId);
                    return true;
                }
            } else {
                tourReviewRepository.deleteByIdAndUserId(reviewId, userId);
                return true;
            }
        } catch (Exception e) {
            logger.error("deleteReview - " + role + " : " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteReview(int reviewId) {
        try {
            if (findById(reviewId) != null) {
                tourReviewRepository.deleteById(reviewId);
                return true;
            }
        } catch (Exception e) {
            logger.error("deleteReview - ROLE_DEV : " + e.getMessage());
        }

        return false;
    }

    private boolean isSearchDate(Date date) {
        if (date != null) {
            return true;
        } else {
            return false;
        }
    }

    // 일반 사용자의 여행 후기 조회
    private List<TourReviewDTO> allReviewListOfUser(int userId, String title, Date startDate, Date endDate) {
        try {
            // 기간을 적었는지 체크
            boolean searchDate = isSearchDate(startDate);

            if (!title.equals("")) {
                if (searchDate) {
                    // 후기 제목으로 검색 (기간)
                    return TourReviewDTO.of(tourReviewRepository.findAllByUserIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(userId, title, startDate, endDate));
                } else {
                    // 후기 제목으로 검색
                    return TourReviewDTO.of(tourReviewRepository.findAllByUserIdAndTitleContainingOrderByCreatedTimeDesc(userId, title));
                }
            } else {
                if (searchDate) {
                    // 전체 조회 (기간)
                    return TourReviewDTO.of(tourReviewRepository.findAllByUserIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(userId, startDate, endDate));
                } else {
                    // 전체 조회
                    return TourReviewDTO.of(tourReviewRepository.findAllByUserIdOrderByCreatedTimeDesc(userId));
                }
            }
        } catch (Exception e) {
            logger.error("allReviewListOfUser userId : " + userId + ", q : " + title + ", error : " + e.getMessage());
        }

        return new  ArrayList<>();
    }

    // 관광지 관리자의 여행 후기 조회
    private List<TourReviewDTO> allReviewListOfAdmin(int userId, int tourId, String title, Date startDate, Date endDate) {
        try {
            boolean searchDate = isSearchDate(startDate);

            if (!title.equals("")) {
                if (searchDate) {
                    if (tourId != 0) {
                        // 관광지 별 후기 제목으로 검색 (기간)
                        return TourReviewDTO.of(tourReviewRepository.findAllByTourIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(tourId, title, startDate, endDate));
                    } else {
                        // 후기 제목으로 검색 (기간)
                        List<TourReview> tourReviews = new ArrayList<>();
                        List<Integer> tourIds = tourInfoService.findAllIdByUserId(userId);

                        if (!tourIds.isEmpty()) {
                            for (int tourInfoId : tourIds) {
                                List<TourReview> tourReviewsByTourId = tourReviewRepository.findAllByTourIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(tourInfoId, title, startDate, endDate);
                                tourReviews.addAll(tourReviewsByTourId);
                            }
                        }

                        return TourReviewDTO.of(tourReviews);
                    }
                } else {
                    if (tourId != 0) {
                        // 관광지 별 후기 제목으로 검색
                        return TourReviewDTO.of(tourReviewRepository.findAllByTourIdAndTitleContainingOrderByCreatedTimeDesc(tourId, title));
                    } else {
                        // 후기 제목으로 검색
                        List<TourReview> tourReviews = new ArrayList<>();
                        List<Integer> tourIds = tourInfoService.findAllIdByUserId(userId);

                        if (!tourIds.isEmpty()) {
                            for (int tourInfoId : tourIds) {
                                List<TourReview> tourReviewsByTourId = tourReviewRepository.findAllByTourIdAndTitleContainingOrderByCreatedTimeDesc(tourInfoId, title);
                                tourReviews.addAll(tourReviewsByTourId);
                            }
                        }

                        return TourReviewDTO.of(tourReviews);
                    }
                }
            } else {
                if (searchDate) {
                    if (tourId != 0) {
                        // 관광지 별 검색 (기간)
                        List<TourReview> tourReviews = tourReviewRepository.findAllByTourIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(tourId, startDate, endDate);
                        return TourReviewDTO.of(tourReviews);
                    }

                    // 전체 조회 (기간)
                    List<TourReview> tourReviews = new ArrayList<>();
                    List<Integer> tourIds = tourInfoService.findAllIdByUserId(userId);

                    if (!tourIds.isEmpty()) {
                        for (int tourInfoId : tourIds) {
                            tourReviews.addAll(tourReviewRepository.findAllByTourIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(tourInfoId, startDate, endDate));
                        }
                    }

                    return TourReviewDTO.of(tourReviews);
                } else {
                    if (tourId != 0) {
                        // 관광지 별 검색
                        List<TourReview> tourReviews = tourReviewRepository.findAllByTourIdOrderByCreatedTimeDesc(tourId);
                        return TourReviewDTO.of(tourReviews);
                    }

                    // 전제 조회
                    List<TourReview> tourReviews = new ArrayList<>();
                    List<Integer> tourIds = tourInfoService.findAllIdByUserId(userId);

                    if (!tourIds.isEmpty()) {
                        for (int tourInfoId : tourIds) {
                            tourReviews.addAll(tourReviewRepository.findAllByTourIdOrderByCreatedTimeDesc(tourInfoId));
                        }
                    }

                    return TourReviewDTO.of(tourReviews);
                }
            }
        } catch (Exception e) {
            logger.error("allReviewListOfAdmin userId : " + userId + ", tourId : " + tourId + ", q : " + title + ", error : " + e.getMessage());
        }

        return new ArrayList<>();
    }

    // 개발자의 여행 후기 조회
    private List<TourReviewDTO> allReviewListOfDev(int type, String q, Date startDate, Date endDate) {
        try {
            boolean searchDate = isSearchDate(startDate);

            if (type == REVIEW_TYPE_ACCOUNT) {
                if (!q.equals("")) {
                    int userId = userService.findIdByUsername(q);

                    if (searchDate) {
                        // 사용자 아이디로 검색 (기간)
                        return TourReviewDTO.of(tourReviewRepository.findAllByUserIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(userId, startDate, endDate));
                    } else {
                        // 사용자 아이디로 검색
                        return TourReviewDTO.of(tourReviewRepository.findAllByUserIdOrderByCreatedTimeDesc(userId));
                    }
                }
            } else if (type == REVIEW_TYPE_TITLE) {
                if (!q.equals("")) {
                    List<Integer> tourIds = tourInfoService.findAllIdByTitle(q);

                    if (!tourIds.isEmpty()) {
                        if (searchDate) {
                            // 관광지로 검색 (기간)
                            List<TourReview> tourReviews = new ArrayList<>();

                            for (int tourInfoId : tourIds) {
                                List<TourReview> tourReviewsByTourId = tourReviewRepository.findAllByTourIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(tourInfoId, startDate, endDate);
                                tourReviews.addAll(tourReviewsByTourId);
                            }

                            return TourReviewDTO.of(tourReviews);
                        } else {
                            // 관광지로 검색
                            List<TourReview> tourReviews = new ArrayList<>();

                            for (int tourInfoId : tourIds) {
                                List<TourReview> tourReviewsByTourId = tourReviewRepository.findAllByTourIdOrderByCreatedTimeDesc(tourInfoId);
                                tourReviews.addAll(tourReviewsByTourId);
                            }

                            return TourReviewDTO.of(tourReviews);
                        }
                    }
                }
            }

            if (searchDate) {
                // 전체 조회 (기간)
                return TourReviewDTO.of(tourReviewRepository.findAllByCreatedTimeBetweenOrderByCreatedTimeDesc(startDate, endDate));
            } else {
                // 전체 조회
                return TourReviewDTO.of(tourReviewRepository.findAllByOrderByCreatedTimeDesc());
            }
        } catch (Exception e) {
            logger.error("allReviewListOfDev type : " + type + ", q : " + q + ", error : " + e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public TourReviewDTO.FileDTO findFileInfoById(int id) {
        try {
            TourReviewDTO tourReviewDTO = TourReviewDTO.of(tourReviewRepository.findById(id));

            if (tourReviewDTO != null) {
                return getFileDTO(id, tourReviewDTO.getFirstImage());
            }
        } catch (Exception e) {
            logger.error("findFileInfoById : " + e.getMessage());
        }

        return null;
    }

    @Override
    public TourReviewDTO.FileDTO saveFile(TourReviewDTO.FileDTO fileDTO) {
        try {
            TourReviewDTO tourReviewDTO = TourReviewDTO.of(tourReviewRepository.findById(fileDTO.getReviewId()));

            if (tourReviewDTO != null) {
                tourReviewDTO.setFirstImage(fileDTO.getPath());
                tourReviewDTO = save(tourReviewDTO);
                logger.info("saveFile : " + fileDTO.getPath());
                return getFileDTO(tourReviewDTO.getId(), tourReviewDTO.getFirstImage());
            }
        } catch (Exception e) {
            logger.error("saveFile : " + e.getMessage());
        }

        return null;
    }

    private TourReviewDTO.FileDTO getFileDTO(int id, String path) {
        TourReviewDTO.FileDTO fileDTO = new TourReviewDTO.FileDTO();
        fileDTO.setReviewId(id);
        fileDTO.setPath(path);
        return fileDTO;
    }

}
