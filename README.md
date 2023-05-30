# 🙌  영화 커뮤니케이션 웹사이트 

## ❓   개발목표
- **최고의 성능으로 빠른 응답 처리시간 고려**
- **깔끔한 클린코드로 인한 코드 가독성 고려**
- **최대한 REST API 의 규격/프로토컬에 맞도록 개발 고려**
- **견고한 API 개발에 대한 고려**
- **예외오류상황에 대한 전역에러 처리 고려**
- **평소에 써보지 않았던 기술(NOSQL)도 같이 접목하여 개발 고려**
- **테스트코드 작성으로 인한 꼼꼼한 API 오류 체크**
- **배포 자동화를 위한 CI/CD 구축**
- **중요 정보 해킹방지를 위한 보안 강화**

##  🙋   SKILL
- **SpringBoot 2.7.3**, **Spring Security**
- **Java 11**, **Jwt**, **Mockito**, **Junit5**
- **MongoDB**, **Spring Data MongoDB**
- **Redis**, **Swagger**

<br>  

## 🛠 배포서버 
# Amazon Cloud EC2
 - Ubuntu 22.04.2 LTS


<br>   

# 개발내용 및 트러블슈팅
- ### 적절한 JWT 사용으로 인한 보안 강화
   **사용자의 인증 또는 중요한 인가 정보의 안전을 위해 JWT를 사용하였다. access Token 뿐 아니라, refresh Token을 이용하여, 중요정보 탈취를 조금이나마 더 방지하고자 하였다. access Token은 5분, refresh Token은 10시간으로 세팅 하여, access Token의 유효시간이 만료될때마다 refresh Token 정보를 확인후 토큰 재발급 해주는 구조로 설계하였다. 또한 refresh Token 마저 만료된다면, 사용자가 로그인을 다시 하게끔 유도하도록 로그인페이지로 튕겨내어 다시 재발급 받도록 설계하였다.**
<br>

- ### 외부API 요청 시 성능 고려
   **restTemplate 대신 비동기적으로 요청하는 non-blocking 처리방식을 이용한 webClient를 활용하였다. 또한 요청을 보내고 응답을 받을 때까지 대기하지 않기 때문에 처리 속도가 훨씬 빠르며, 사용하는 외부 API의 데이터가 대용량 데이터이기 때문에 처리하기에 용이 하여 활용하였다.**
<br>

- ### 목록 조회 요청 최적화 성능 고려
   **현재는 크게 성능차이가 나지는 않지만, 추후 확장성을 고려하였을 때, 자주 반복되는 리뷰목록 조회 API를 활용 할때, Redis 캐싱을 이용하여 DB 까지 들리지 않고 캐시에서 꺼내옴으로써 조회성능 최적화를 하였다. 또한 리뷰가 작성되면 전체 캐시를 한번 날려주고 목록을 조회할때 캐시를 다시 갱신해줌으로써 캐시데이터 정합성 문제를 해결하였다.**
<br>

- ### 예외오류상황에 대한 전역에러 처리 고려
   **try-catch문으로 예외오류상황을 처리 해오는 방식 대신, 코드 가독성 혹은 생산성을 위해 스프링에서 제공하는 @RestControllerAdvice를 활용하였다. 추가적으로 오류내용과 오류코드에 대한 정보를 주고받기 위해 객체 단위로 묶어 반환을 해주었다.**
<br>   

- ### 서버 배포시 yml 파일분리하여 배포 고려
   **토큰 정보나, api-key, DB info 등 중요한정보가 들어있는 yml 파일은 클라우드 서버에 따로 파일을 생성해, jar를 구동할때 yml 파일을 읽도록 따로 분리해놓았다.**
<br>   

- ### 레디스, 몽고디비 해킹당하다.
   **mysql과 같은 비밀번호 설정이 먼저 가능한 RDMS만 사용하다가 비밀번호 설정이 까다로운 MongoDB를 사용하였다. 비밀번호 설정에 대해 인지를 하지못하고, MongoDB에 대한 접속정보만 입력 한뒤 개발을 하고 있었다. 테스트를 위해 더미데이터를 몇개 넣어놨는데, 자꾸 데이터가 사라지고 비트코인 을 달라는 ReadMe 파일이 몽고디비 내에 생성이 되어있었다. 그래서 심각성을 깨닫고 몽고디비 설정파일을 들어가, 비밀번호를 세팅하였다. 레디스도 마찬가지다.**
 <br>[몽고디비 해킹당하다.](https://yjkim-dev.tistory.com/64)
<br>   

- ### 배포 자동화 고려
   **빌드하여 빌드된 파일가지고 서버로 접속해, 배포하는 반복되고 지루한 배포경험은 누구나 있을것이다. 하지만 요새는 좋은 빌드자동화 툴도 많이 나와있다. 그래서 이런걸 안 쓸 이유가 없었다. 그래서 githook을 jenkins에 걸어, 사용하는 서버에 빌드파일을 떨궈 기존에 있던 포트를 제거 후 빌드파일을 실행하도록 groovy 스크립트를 작성해 파이프라인 CI/CD를 구축하였다.**
<br>   

- ### Restful 스러운 api 설계 및 가독성 좋은 코드 설계
   **Rest-api가 강력하게 원하는 코드스타일이 있다. 그 코드스타일에 맞춰 개발하려고 노력하였고 필요없는 로직을 제거 후 리펙토링 하여 가독성이 좋게 코드를 짯다고 자부한다.**
<br>   
   
## ❓ 프로젝트 화면

url : http://15.165.236.184:3000/


![메인1](https://github.com/MovieApplication/backend/assets/73875312/1b7d9ef5-623d-4a21-a4b5-b30e219c41fb)

![메인2](https://github.com/MovieApplication/backend/assets/73875312/8bd45cfc-66ec-4f10-a98b-c7402ae99b63)

![상세1](https://github.com/MovieApplication/backend/assets/73875312/fd23f128-d91e-4aa0-a0f7-f6af5134acf8)


## 🚜ERD

![ERD](https://github.com/MovieApplication/backend/assets/73875312/e36b5b4a-a082-4243-b91c-5b7b9dbdd1e7)


