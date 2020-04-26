# 관광지 방문 인증 웹 프로젝트 API

&nbsp;

&nbsp;

&nbsp;

**작성자 : 김보현, 박혜빈**

&nbsp;

&nbsp;

&nbsp;

### 1. Server, Database

#### 1-1. Host

- [개발서버] 	http://106.10.40.191:8080
- [운영서버] 

&nbsp;

&nbsp;

#### 1-2. Database table

```text
1. TOUR_INFO : 관광지, 행사/축제, 문화 등 정보
2. TOUR_REVIEW : 관광지 방문 후기
3. AREA_CODE : 지역코드
4. SIGUNGU_CODE : 시군구코드
5. CONTENT_TYPE : 콘텐츠 타입
	5-1. 12 : 관광지
	5-2. 14 : 문화시설
	5-3. 15 : 행사/공연/축제
	5-4. 25 : 여행코스
	5-5. 28 : 레포츠
	5-6. 38 : 쇼핑
	5-7. 39 : 음식점
6. CAT1 : 대분류
7. CAT2 : 중분류
8. CAT3 : 소분류
9. NOTICE : 공지사항
10. QNA
11. FAQ
12. USER : 사용자 정보
13. ROLE : 사용자 권한 정보
	13-1. ROLE_USER (LV_1) : 일반사용자
	13-2. ROLE_ADMIN (LV_2) : 관광지 관리자
	13-3. ROLE_DEV (LV_3) : 개발자
14. USER_ROLE
```

&nbsp;

&nbsp;

### 2. API

#### 2-1. 관광지 방문 후기 (TourReview API)

##### 1. 여행 후기 최신순 10개 조회

[Request]

- path : {host}/review/recent
- method : GET
- example

```
http://106.10.40.191:8080/review/recent
```

&nbsp;

[Response]

- parameters



- example

[성공]

```json

```



[실패]

```json

```

------

&nbsp;

&nbsp;

##### 2. 사용자의 지역별 후기 개수

[Request]

- path : {host}/review/count
- method : GET
- parameters

| name     | type | desc     |
| -------- | ---- | -------- |
| areaCode | int  | 지역코드 |




- example

```
http://106.10.40.191:8080/review/count?areaCode=31
```

&nbsp;

[Response]

- parameters



- example

[성공]

```json

```



[실패]

```json

```
------

&nbsp;

&nbsp;

##### 3. 여행 후기 등록

[Request]

- path : {host}/review
- method : POST
- parameters

| name        | type    | desc                                   |
| ----------- | ------- | -------------------------------------- |
| areaCode    | int     | 지역코드                               |
| sigunguCode | int     | 시군구코드                             |
| title       | string  | 관광지명 또는 후기 제목                |
| overview    | string  | 후기 내용                              |
| tourId      | int     | 관광지 인덱스                          |
| isPrivate   | boolean | 공개여부 (true = 비공개, false = 공개) |




- example

```json
{
	"areaCode":"31",
	"sigunguCode":"9",
	"title":"수락산 계곡",
	"overView":"우와~",
	"tourId":1
}
```

&nbsp;

[Response]

- parameters



- example

[성공]

```json

```



[실패]

```json

```
------

&nbsp;

&nbsp;

##### 4. 여행 후기 전체 조회

[Request]

- path : {host}/review
- method : GET
- parameters

| name      | type   | desc                                                    |
| --------- | ------ | ------------------------------------------------------- |
| page      | int    | 현재 페이지 넘버                                        |
| size      | int    | 페이지 별 조회할 건수                                   |
| type      | int    | 검색 타입 (0. 전체, 1. 아이디로 조회, 2. 관광지로 조회) |
| tourId    | int    | 관광지 인덱스 (type = 2 일 경우)                        |
| q         | string | 검색어                                                  |
| startDate | string | 검색 시작일 (yyyyMMdd)                                  |
| endDate   | string | 검색 종료일 (yyyyMMdd)                                  |




- example

```

```

&nbsp;

[Response]

- parameters



- example

[성공]

```json

```



[실패]

```json

```
------

&nbsp;

&nbsp;

#### 2-2. 관광지 방문 후기 이미지 파일 관리 (TourReview File API)

##### 1. 파일 저장

[Request]


- path : {host}/file
- method : POST
- parameters

| name     | type          | desc                                      |
| -------- | ------------- | ----------------------------------------- |
| reviewId | int           | 여행 후기 인덱스                          |
| file     | MultipartFile | 여행 후기 이미지 파일 (.JPG, .JPEG, .PNG) |




- example

```

```

&nbsp;

[Response]

- parameters



- example

[성공]

```json

```



[실패]

```json

```
------

&nbsp;

&nbsp;

##### 2. 파일 조회

[Request]


- path : {host}/review
- method : GET
- parameters

| name     | type | desc             |
| -------- | ---- | ---------------- |
| reviewId | int  | 여행 후기 인덱스 |




- example

```

```

&nbsp;

[Response]

- parameters



- example

[성공]

```json

```



[실패]

```json

```
------

&nbsp;

&nbsp;

##### 3. 파일 삭제 

[Request]


- path : {host}/review
- method : DELETE
- parameters

| name     | type | desc             |
| -------- | ---- | ---------------- |
| reviewId | int  | 여행 후기 인덱스 |




- example

```

```

&nbsp;

[Response]

- parameters



- example

[성공]

```json

```



[실패]

```json

```
------

&nbsp;

&nbsp;

#### 2-3. 관광지 조회 (Search API)

[Request]


- path : {host}/search/tour-info
- method : GET
- parameters

| name          | type   | desc                                   | 필수값           |
| ------------- | ------ | -------------------------------------- | ---------------- |
| page          | int    | 현재 페이지 넘버                       | N (default = 0)  |
| size          | int    | 페이지 별 조회할 건수                  | N (default = 10) |
| type          | int    | 정렬 기준 (1. 등록일순, 2. 관광지명순) | N (default = 1)  |
| sort          | int    | 정렬 (1. 내림차순, 2. 오름차순)        | N (default = 1)  |
| areaCode      | int    | 지역코드                               | N                |
| sigunguCode   | int    | 시군구코드                             | N                |
| contentTypeId | int    | 콘텐츠 타입 (값이 없으면 전체 조회)    | N (default = 0)  |
| cat1          | string | 대분류                                 | N                |
| cat2          | string | 중분류                                 | N                |
| cat3          | string | 소분류                                 | N                |
| keyword       | string | 키워드                                 | N                |

```text
[contentTypeId]
12 : 관광지, 
14 : 문화시설, 
15 : 행사/공연/축제,
25 : 여행코스, 
28 : 레포츠, 
38 : 쇼핑, 
39 : 음식점
```


- example

```

```

&nbsp;

[Response]

- parameters



- example

[성공]

```json

```



[실패]

```json

```
------

&nbsp;

&nbsp;