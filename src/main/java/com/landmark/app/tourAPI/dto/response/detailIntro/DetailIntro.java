package com.landmark.app.tourAPI.dto.response.detailIntro;

import lombok.Data;

import java.math.BigInteger;

@Data
public class DetailIntro {

    private int contentId;
    private int contentTypeId;
    /** contentTypeId=12 (관광지) **/
    private String acComCount;                      // 수용인원
    private String chkBabyCarriage;                 // 유모차 대여 정보
    private String chkCreditCard;                   // 신용카드 가능 정보
    private String chkPet;                          // 애완동물 동반 가능 정보
    private String expAgeRange;                        // 체험가능 연령
    private String expGuide;                        // 체험 안내
    private String infoCenter;                      // 문의 및 안내
    private BigInteger openDate;                    // 개장일
    private String parking;                         // 주차시설
    private String restDate;                        // 쉬는날
    private String useTime;                         // 이용시간
    /** contentTypeId=14 (문화시설) **/
    private String acComCountCulture;
    private String chkBabyCarriageCulture;
    private String chkCreditCardCulture;
    private String chkPetCulture;
    private String discountInfo;                    // 할인정보
    private String infoCenterCulture;
    private String parkingCulture;
    private String restDateCulture;
    private String useFee;                          // 이용요금
    private String useTimeCulture;
    private String spendTime;                       // 관람소요시간
    /** contentTypeId=15 (행사/공연/축제) **/
    private String ageLimit;                        // 관람 가능 연령
    private String bookingPlace;                    // 예매처
    private String discountInfoFestival;
    private BigInteger eventStartDate;              // 행사 시작일
    private BigInteger eventEndDate;                // 행사 종료일
    private String eventHomePage;                   // 행사 홈페이지
    private String eventPlace;                      // 행사 장소
    private String playTime;                        // 공연 시간
    private String sponsor1;                        // 주최자 정보
    private String sponsor1tel;                     // 주최자 연락처
    private String sponsor2;                        // 주관사 정보
    private String sponsor2tel;                     // 주관사 연락처
    private String useTimeFestival;                 // 이용요금
    /** contentTypeId=25 (여행코스) **/
    private String distance;                        // 코스 총거리
    private String infoCenterTourCourse;
    private String schedule;                        // 코스 일정
    private String takeTime;                        // 코스 총 소요시간
    private String theme;                           // 코스 테마
    /** contentTypeId=28 (레포츠) **/
    private String acComCountLeports;
    private String chkBabyCarriageLeports;
    private String chkCreditCardLeports;
    private String chkPetLeports;
    private String expAgeRangeLeports;
    private String infoCenterLeports;
    private String openPeriod;                      // 개장기간
    private String parkingLeports;
    private String reservation;                     // 예약안내
    private String restDateLeports;
    private String UseFeeLeports;
    private String useTimeLeports;

}
