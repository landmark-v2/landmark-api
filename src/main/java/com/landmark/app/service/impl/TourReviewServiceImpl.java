package com.landmark.app.service.impl;

import com.landmark.app.model.domain.TourReview;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.model.repository.TourReviewRepository;
import com.landmark.app.service.TourInfoService;
import com.landmark.app.service.TourReviewService;
import com.landmark.app.service.user.UserService;
import com.landmark.app.utils.constants.Constants;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.landmark.app.utils.constants.Constants.*;

@Service
public class TourReviewServiceImpl extends LoggerUtils implements TourReviewService {

    private TourReviewRepository tourReviewRepository;
    private TourInfoService tourInfoService;
    private UserService userService;

    @Autowired
    public TourReviewServiceImpl(TourReviewRepository tourReviewRepository, TourInfoService tourInfoService, UserService userService) {
        this.tourReviewRepository = tourReviewRepository;
        this.tourInfoService = tourInfoService;
        this.userService = userService;
    }

    @Override
    public TourReviewDTO save(TourReviewDTO tourReviewDTO) {
        try {
            tourReviewDTO.setModifiedTime(new Date());
            TourReview tourReview = tourReviewRepository.saveAndFlush(TourReview.of(tourReviewDTO));
            return TourReviewDTO.of(tourReview);
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
                Map<Integer, Integer> sigunguPkMap = new HashMap<>();
                sigunguPkMap.put(tourReviewDTO.getAreaCode(), tourReviewDTO.getSigunguCode());

                String areaName = Constants.areaCodeMap.get(tourReviewDTO.getAreaCode());
                String sigunguName = Constants.sigunguCodeMap.get(sigunguPkMap);

                recentReviews.add(getRecentReview(areaName, sigunguName, tourReviewDTO.getFirstImage()));
            }
        } catch (Exception e) {
            logger.error("getRecentHistories : " + e.getMessage());
        }

        return recentReviews;
    }

    private TourReviewDTO.RecentReview getRecentReview(String areaName, String sigunguName, String firstImage) {
        TourReviewDTO.RecentReview recentReview = new TourReviewDTO.RecentReview();
        recentReview.setAreaName(areaName);
        recentReview.setSigunguName(sigunguName);
        recentReview.setFirstImage(firstImage);
        return recentReview;
    }

    @Override
    public int countByAreaCode(int areaCode, int userId) {
        try {
            return tourReviewRepository.countAllByAreaCodeAndUserId(areaCode, userId);
        } catch (Exception e) {
            logger.error("countByAreaCode : " + e.getMessage());
            return 0;
        }
    }

    @Override
    public TourReviewDTO registerReview(TourReviewDTO tourReviewDTO) {
        tourReviewDTO.setCreatedTime(new Date());
        return save(tourReviewDTO);
    }

    @Override
    public Page<TourReviewDTO> getReviewList(int userId, String roleName, TourReviewDTO.SearchReviewDTO searchReviewDTO) {
        int page = searchReviewDTO.getPage();
        int size = searchReviewDTO.getSize() == 0 ? 10 : searchReviewDTO.getSize();

        String q = searchReviewDTO.getQ() != null ? searchReviewDTO.getQ() : "";
        Date startDate = searchReviewDTO.getStartDate();
        Date endDate = searchReviewDTO.getEndDate();

        Pageable pageable = PageRequest.of(page, size);

        if (roleName.equals(ROLE_USER)) {
            return allReviewListOfUser(userId, q, startDate, endDate, pageable);
        } else if (roleName.equals(ROLE_ADMIN)) {
            int tourId = searchReviewDTO.getTourId();
            return allReviewListOfAdmin(userId, tourId, q, startDate, endDate, pageable);
        } else if (roleName.equals(ROLE_DEV)) {
            int type = searchReviewDTO.getType() <= REVIEW_TYPE_TITLE ? searchReviewDTO.getType() : 0;
            return allReviewListOfDev(type, q, startDate, endDate, pageable);
        }

        return new PageImpl<>(new ArrayList<>());
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
    public TourReviewDTO updateReview(TourReviewDTO.UpdateReviewDTO updateReviewDTO, int userId) {
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
    private Page<TourReviewDTO> allReviewListOfUser(int userId, String title, Date startDate, Date endDate, Pageable pageable) {
        try {
            // 기간을 적었는지 체크
            boolean searchDate = isSearchDate(startDate);

            if (!title.equals("")) {
                if (searchDate) {
                    // 후기 제목으로 검색 (기간)
                    return TourReviewDTO.of(tourReviewRepository.findAllByUserIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(userId, title, startDate, endDate, pageable));
                } else {
                    // 후기 제목으로 검색
                    return TourReviewDTO.of(tourReviewRepository.findAllByUserIdAndTitleContainingOrderByCreatedTimeDesc(userId, title, pageable));
                }
            } else {
                if (searchDate) {
                    // 전체 조회 (기간)
                    return TourReviewDTO.of(tourReviewRepository.findAllByUserIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(userId, startDate, endDate, pageable));
                } else {
                    // 전체 조회
                    return TourReviewDTO.of(tourReviewRepository.findAllByUserIdOrderByCreatedTimeDesc(userId, pageable));
                }
            }
        } catch (Exception e) {
            logger.error("allReviewListOfUser userId : " + userId + ", q : " + title + ", error : " + e.getMessage());
        }

        return new PageImpl<>(new ArrayList<>());
    }

    // 관광지 관리자의 여행 후기 조회
    private Page<TourReviewDTO> allReviewListOfAdmin(int userId, int tourId, String title, Date startDate, Date endDate, Pageable pageable) {
        try {
            boolean searchDate = isSearchDate(startDate);

            if (!title.equals("")) {
                if (searchDate) {
                    if (tourId != 0) {
                        // 관광지 별 후기 제목으로 검색 (기간)
                        return TourReviewDTO.of(tourReviewRepository.findAllByTourIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(tourId, title, startDate, endDate, pageable));
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

                        return new PageImpl<>(TourReviewDTO.of(tourReviews), pageable, tourReviews.size());
                    }
                } else {
                    if (tourId != 0) {
                        // 관광지 별 후기 제목으로 검색
                        return TourReviewDTO.of(tourReviewRepository.findAllByTourIdAndTitleContainingOrderByCreatedTimeDesc(tourId, title, pageable));
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

                        return new PageImpl<>(TourReviewDTO.of(tourReviews), pageable, tourReviews.size());
                    }
                }
            } else {
                if (searchDate) {
                    if (tourId != 0) {
                        // 관광지 별 검색 (기간)
                        List<TourReview> tourReviews = tourReviewRepository.findAllByTourIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(tourId, startDate, endDate);
                        return new PageImpl<>(TourReviewDTO.of(tourReviews), pageable, tourReviews.size());
                    }

                    // 전체 조회 (기간)
                    List<TourReview> tourReviews = new ArrayList<>();
                    List<Integer> tourIds = tourInfoService.findAllIdByUserId(userId);

                    if (!tourIds.isEmpty()) {
                        for (int tourInfoId : tourIds) {
                            tourReviews.addAll(tourReviewRepository.findAllByTourIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(tourInfoId, startDate, endDate));
                        }
                    }

                    return new PageImpl<>(TourReviewDTO.of(tourReviews), pageable, tourReviews.size());
                } else {
                    if (tourId != 0) {
                        // 관광지 별 검색
                        List<TourReview> tourReviews = tourReviewRepository.findAllByTourIdOrderByCreatedTimeDesc(tourId);
                        return new PageImpl<>(TourReviewDTO.of(tourReviews), pageable, tourReviews.size());
                    }

                    // 전제 조회
                    List<TourReview> tourReviews = new ArrayList<>();
                    List<Integer> tourIds = tourInfoService.findAllIdByUserId(userId);

                    if (!tourIds.isEmpty()) {
                        for (int tourInfoId : tourIds) {
                            tourReviews.addAll(tourReviewRepository.findAllByTourIdOrderByCreatedTimeDesc(tourInfoId));
                        }
                    }

                    return new PageImpl<>(TourReviewDTO.of(tourReviews), pageable, tourReviews.size());
                }
            }
        } catch (Exception e) {
            logger.error("allReviewListOfAdmin userId : " + userId + ", tourId : " + tourId + ", q : " + title + ", error : " + e.getMessage());
        }

        return new PageImpl<>(new ArrayList<>());
    }

    // 개발자의 여행 후기 조회
    private Page<TourReviewDTO> allReviewListOfDev(int type, String q, Date startDate, Date endDate, Pageable pageable) {
        try {
            boolean searchDate = isSearchDate(startDate);

            if (type == REVIEW_TYPE_ACCOUNT) {
                if (!q.equals("")) {
                    int userId = userService.findIdByUsername(q);

                    if (searchDate) {
                        // 사용자 아이디로 검색 (기간)
                        return TourReviewDTO.of(tourReviewRepository.findAllByUserIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(userId, startDate, endDate, pageable));
                    } else {
                        // 사용자 아이디로 검색
                        return TourReviewDTO.of(tourReviewRepository.findAllByUserIdOrderByCreatedTimeDesc(userId, pageable));
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

                            return new PageImpl<>(TourReviewDTO.of(tourReviews), pageable, tourReviews.size());
                        } else {
                            // 관광지로 검색
                            List<TourReview> tourReviews = new ArrayList<>();

                            for (int tourInfoId : tourIds) {
                                List<TourReview> tourReviewsByTourId = tourReviewRepository.findAllByTourIdOrderByCreatedTimeDesc(tourInfoId);
                                tourReviews.addAll(tourReviewsByTourId);
                            }

                            return new PageImpl<>(TourReviewDTO.of(tourReviews), pageable, tourReviews.size());
                        }
                    }
                }
            }

            if (searchDate) {
                // 전체 조회 (기간)
                return TourReviewDTO.of(tourReviewRepository.findAllByCreatedTimeBetweenOrderByCreatedTimeDesc(startDate, endDate, pageable));
            } else {
                // 전체 조회
                return TourReviewDTO.of(tourReviewRepository.findAllByOrderByCreatedTimeDesc(pageable));
            }
        } catch (Exception e) {
            logger.error("allReviewListOfDev type : " + type + ", q : " + q + ", error : " + e.getMessage());
        }

        return new PageImpl<>(new ArrayList<>());
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
