# SFlash ✨
<br>

## 💡 프로젝트 소개
SFlash 는 자신만의 스팟을 저장하고 공유하는 서비스입니다.
<br>

#### 팀원
* Frontend : 허민규, 김다영, 김형민 `React`
* Backend : 장현준, 김승욱, 이세정 `SpringBoot`
* Designer : 송은정, 임아현

#### 진행 기간
* 21.04.23(금) - 21.05.28(금)

#### 기획 배경
* 원하는 장소를 찾기 위해 인스타그램, 블로그 검색 후 해당 장소의 정확한 위치를 찾기 위해 지도로 검색을 해야하는 번거로움 발생
* 사용자들이 원하는 스팟 정보를 지도와 사진으로 한 눈에 볼 수 있도록 기획
<br>


## 💡 개발 환경
* `Java 8`
* `JDK 1.8.0`
* IDE : `IntelliJ`
* Framework : `SpringBoot`
* Build Tools : `Gradle`
* Server : `Amazon EC2 Ubuntu`
* Database : `Amazon RDS MariaDB`
* CI/CD : `Travis`
<br>

## 💡 전체 구조
![](https://user-images.githubusercontent.com/55679927/119793545-a778e780-bf11-11eb-946e-581fd2063913.jpeg)


## 💡 주요 기능
* 로그인, 회원가입
* 소셜 로그인
* 게시글 CRUD
* 다중 이미지 업로드 
* 댓글 CRUD
* 좋아요
* 프로필 편집
* 문의하기 CRUD
* 문의하기 답변 CRUD
<br>

### ▶ SFlash 회원가입, 로그인, jwt 정리
- jwt 토큰

  - 로그인 요청이 들어온 후 정보가 맞으면 jwtTokenProvider에서 createToken 함수를 이용해서 jwt 토큰을 생성한다.

    ![createToken](https://user-images.githubusercontent.com/70622731/119794050-21a96c00-bf12-11eb-960f-de4383b2d2cf.PNG)

  - jwtAuthenticationFilter를 만들어서 UsernamePasswordAuthenticationFilter보다 먼저 실행되게 해서 jwt 토큰이 유효한지 판단한다. (인증이 필요한 api에서 jwt 토큰을 보낼때 확인하는 용도)

  - jwt 생성할때 토큰 정보에 email을 넣었기 때문에 jwt 정보를 꺼내서 loadUserByUsername으로 보낼때 email값이 넘어가게 된다.

  - jwtTokenProvider에서 loadUserByUsername을 호출하면  CustomUserDetailsService은 UserDetailsService interface를 구현했기 때문에 @Override한 CustomUserDetailsService의 loadUserByUsername이 호출된다. 

    ![loadUserByUsername](https://user-images.githubusercontent.com/70622731/119794083-2e2dc480-bf12-11eb-95d8-e2fa2ea0092d.PNG)

  - CustomUserDetailsService의 loadUserByUsername는 받은 email 값으로 userRepository에서 findByEmail을 찾아 User를 리턴해준다.
  - 그래서 @AuthenticationPrincipal을 사용하면 토큰에서 User 정보를 받을 수 있는 것 같다.



- 로그인/회원가입 flow chart

  <img src = "https://user-images.githubusercontent.com/70622731/119794137-3ab21d00-bf12-11eb-8344-446965e1d39d.PNG" width="70%">

  

- 이메일/비밀번호 찾기 flow chart

  <img src = "https://user-images.githubusercontent.com/70622731/119794165-41d92b00-bf12-11eb-9db0-2be808ef4b73.PNG" width="60%">

  

- 로그인

  - 이메일, 비밀번호가 user테이블에 등록되어 있으면 jwtTokenProvider에서 createToken 함수에 user.getEmail을 넣어서 토큰을 생성한다. 

- 회원가입

  - Dto에서 @Valid를 통한 유효성 검사

  - 비밀번호와 비밀번호 체크가 맞는지 검사

  - 닉네임 & 이메일 중복확인

  - 가입하려는 이메일이 이메일 인증이 된 상태인지 확인 email_check 테이블에 이메일 값이 존재하고 authCode 값이 "Y"일 경우 인증되었다고 판단

    ![signup](https://user-images.githubusercontent.com/70622731/119794239-51f10a80-bf12-11eb-9c91-06777085dd40.PNG)

- 닉네임중복체크

  - 닉네임이 user 테이블에 존재하면 false 반환, 존재하지않으면 true 반환

- 이메일 중복체크 + 인증번호 발송

  - 이메일이 user 테이블에 존재하면 false 반환, 존재하지않으면 입력한 이메일로 메일 발송하고 true값 반환
  - email_check 테이블에 같은 이메일로 메일발송 요청이 들어오면 authCode만 업데이트 시켜준다.

- 이메일 인증 확인

  - 인증번호를 받은 이메일이 아니면 false 출력, 인증번호가 다르면 false 출력, 인증번호를 받은 이메일이고 authCode가 "Y"일 경우 true 출력

- 이메일 찾기

  - 입력한 nickname이 user 테이블에 존재하면 그 user에 email을 반환해주고 없으면 null을 반환한다.

- 비밀번호 찾기

  - 입력한 email이  user테이블에 존재하면 email로 authCode메일을 발송하고 pwd_check 테이블에 이메일과 authCode를 저장한다, user 테이블이 null이라면 false를 반환한다.
  - pwd_check 테이블에 같은 이메일로 메일발송 요청이 들어오면 authCode만 업데이트 시켜준다.

- 비밀번호 인증 확인

  - email이 pwd_chech 테이블에 없으면 false 반환, 테이블에 저장된 authCode와 입력한 코드가 같다면 "Y"로 변경해주고 true를 리턴한다.

- 비밀번호 수정

  - user 테이블에 입력한 email이 존재하지 않는다면 에러를 보내주고, user테이블에 존재하고 테이블에 존재하는 auth코드가 "Y"일경우 비밀번호를 수정할 수 있게 해준다.

- 관리자 회원가입

  - 기존 회원가입 방식에서 adminToken을 추가해서 회원가입을 하게되면 ADMIN role을 추가해서 관리자로 회원가입 시킨다.



### OAuth2 소셜로그인

- OAuth2 로그인 흐름
  - 사용자 측의 브라우저에서 엔드포인트 `http://{도메인}/oauth2/authorize{provider}?redirect_uri={프론트엔드에서 소셜로그인 후 돌아갈 uri}`로 접속하는 것으로 프론트엔드 클라이언트에서 시작된다.
  - provider 경로 매개변수는 naver, google, kakao중 하나이다.
  - OAuth2 콜백으로 인해 오류가 발생하면 스프링 시큐리티는 설정해놓은 oAuth2AuthenticationFailureHandler를 호출한다.
  - OAuth2 콜백이 성공하고 인증 코드가 포함 된 경우 Spring Security는 access_token에 대한 authorization_code를 교환하고 Security에 지정된 customOAuth2UserService를 호출한다.
  - customOAuth2UserService는 인증된 사용자의 세부 정보를 검색하고 데이터베이스에 새 항목을 작성하거나 동일한 이메일의 정보를 찾아 기존 항목을 업데이트 한다.
  - 마지막으로 oAuth2AuthenticationSuccessHandler가 호출된다. 사용자에 대한 JWT 인증 토큰을 만들고 쿼리 문자열로 JWT 토큰과 함께 사용자를 redirect_uri로 보낸다.


    <img src = "https://user-images.githubusercontent.com/70622731/119793333-74ceef00-bf11-11eb-9c9e-61286f6c2c0d.jpg" width="60%">


- security 설정

  - authoriztionEndpoint()를 `/oauth2/authorize`로 지정한다.
  - redirectionEndpoint()를 `/login/oauth2/code/*`로 지정한다.
  - 성공했을경우 succesHandler로 보낸다.
  - 실패했을경우 failureHandler로 보낸다.

    ![security 캡쳐](https://user-images.githubusercontent.com/70622731/119793494-9def7f80-bf11-11eb-9ad5-6ff23a711ec0.PNG)



- customOAuth2UserService

  - oauth2 를 통해 로그인한 사용자 정보를 받아서 저장하는 역할을 한다.

    ![CustomOAuth2UserService](https://user-images.githubusercontent.com/70622731/119793798-e0b15780-bf11-11eb-9aaa-8b4d834a8cfa.PNG)



- OAuth2UserInfoFactory

  - customOAuth2UserService에서 받은 provider가 google, naver, kakao중에 어떤것인지 판단해 맞는 객체를 생성한다.

    ![OAuth2UserInfoFactory 캡쳐](https://user-images.githubusercontent.com/70622731/119793908-f7f04500-bf11-11eb-9d38-1af66fdbb7aa.PNG)



- oAuth2AuthenticationSuccessHandler

  - jwt 토큰을 생성하고 사용자가 지정한 redirect_uri에 queryParam으로 token을 담아서 보내준다.

    ![oAuth2AuthenticationSuccessHandler](https://user-images.githubusercontent.com/70622731/119793963-063e6100-bf12-11eb-9b6e-641cb6c906b4.PNG)



- UserPrincipal

  - OAuth2로 로그인한 사용자도 담아주기 위해서 UserPrincipal에서 OAuth2User도 implements한다.

    ![UserPrincipal](https://user-images.githubusercontent.com/70622731/119793999-10f8f600-bf12-11eb-8777-366951c3eaaa.PNG)



- oauth2.yml
  - oauth2에 대한 설정을 yml에 다해준다. 구글, 페이스북, 깃허브 같이 oauth2에 provider들은 provider를 따로 써줄필요 없는데 국내 소셜로그인 네이버, 카카오 같은 경우는 oauth2에 provider로 등록이 안되어 있기 때문에 yml에 provider에 대한 설정도 같이 넣어줘야한다.

<br>

### ▶ 마이페이지

* 프로필 정보
   * `/profile/{userId}`
   * url의 `userId`로 유저를 찾아 ProfileResponseDto를 리턴
  
```java
@Getter
@NoArgsConstructor
public class ProfileResponseDto {
    private Long userId;
    private String nickname;
    private String imgUrl;
    private String introduceMsg;

    public ProfileResponseDto(User editUser){
        this.userId = editUser.getId();
        this.nickname = editUser.getNickname();
        this.imgUrl = editUser.getImgUrl();
        this.introduceMsg = editUser.getIntroduceMsg();
    }

}
```

* 유저가 업로드 한 게시물
   * `/story/{userId}/board`
   * 무한 스크롤 방식 적용
   * 유저가 `null`일 경우는 비로그인 회원이 다른 사람의 페이지를 방문했을 경우이므로, 좋아요의 체크 여부를 `false`로 하여 반환
   
* 유저가 좋아요 한 게시물
   * `/story/{userId}/likeboard`
   * 무한 스크롤 방식 적용
   * 좋아요 한 게시물 중 유저가 업로드한 게시물은 제외
   * 유저가 `null`일 경우는 비로그인 회원이 다른 사람의 페이지를 방문했을 경우이므로, 좋아요의 체크 여부를 `false`로 하여 반환

```java
@Getter
@NoArgsConstructor
public class MypageResponseDto {

    //board
    private Long boardId;
    private double latitude;
    private double longitude;
    private String spotName;
    private String category;
    private List<BoardImgCommonRequestDto> boardImgResponseDtoList = new ArrayList<>();

    //heart
    private boolean liked;
    private int likeCount;

    @Builder
    public MypageResponseDto(Board boardEntity, boolean likeCheck, int likeCount, List<BoardImgCommonRequestDto> responseDto) {

        //board 정보
        this.boardId = boardEntity.getId();
        this.category = boardEntity.getCategory();
        this.latitude = boardEntity.getLatitude();
        this.longitude = boardEntity.getLongitude();
        this.spotName = boardEntity.getSpotName();

        //이미지
        this.boardImgResponseDtoList = responseDto;

        //좋아요
        this.liked = likeCheck;
        this.likeCount = likeCount;

        }
    }
```

* 프로필 편집
   * 프로필 이미지, 소개 메시지
      * `/editmyprofile/{userId}`
      * `userId`와 token 속 user를 비교하여 본인만 편집 가능
      * 프로필 이미지를 변경하지 않는 경우에는 imgUrl에 유저의 기존 imgUrl로 설정
      * 프로필 이미지 파일을 받은 경우에는  
          * 기존 파일 이름을 변경. 공백 제거, `.확장자` 앞의 문자 제거 ---> 고유식별자 + 날짜
          * S3에 업로드
```java
public String profileUpload(MultipartFile file, String dirName) throws IOException {
        return changeProfileFileName(file, dirName);
    }

private String changeProfileFileName(MultipartFile uploadFile, String dirName) throws IOException {

        String replace = uploadFile.getOriginalFilename().replace(" ", ""); //공백 다 없애기
        log.info("changeFileName1: " + uploadFile.getOriginalFilename());
        String fileName = replace.substring(uploadFile.getOriginalFilename().lastIndexOf('.')); //.png 즉, 확장자와 . 앞에 문자 다 없애기
        log.info("=======새로운 fileName : " + fileName);
        log.info("changeFileName2: " + fileName);
        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

        //파일 이름을 다르게 한다. 날짜로만헀는데 for문이 너무 빠르게 돌아서 mmss까지 커버가 안되서 교체!
        UUID uuid = UUID.randomUUID();
        String subUUID = uuid.toString().substring(0, 8); //16자리로 생성되는데 너무 길어서 8자리로 짜름!
        SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateUuidFileName = subUUID + fourteen_format.format(date_now) + fileName;
        String resultFileName = dirName + "/" + dateUuidFileName;
        log.info("파일 이름 나타내기 2번째 : " + uploadFile.getName() + " ," + resultFileName);
        String uploadImgUrl = putS3Aws(uploadFile, resultFileName);

        return uploadImgUrl;
    }

    private String putS3Aws(MultipartFile uploadFile, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }
```

   * 닉네임
      * `/editnickname/{userId}`
      * 닉네임 중복확인을 먼저 거치기
      * 본인만 편집 가능
      
   * 비밀번호 변경
      * `/editpwd/{userId}`
      * 본인만 변경 가능
      * PasswordRequestDto에서 `@NotBlank`, `@Pattern` 어노테이션으로 validation체크. 회원가입 시 비밀번호 세팅과 동일하게 맞춰줌
      * `BCryptPasswordEncoder.matches`를 이용하여 원래 비밀번호와 입력 비밀번호가 같은 지 확인
      
 ```java
@Getter
@NoArgsConstructor
public class PasswordRequestDto {
    @NotBlank(message = "비밀번호를 비워둘 수 없습니다.")
    private String pwd;

    @NotBlank(message = "비밀번호를 비워둘 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{10,}$",
            message = "비밀번호 형식을 지켜주세요")
    private String newPwd;

    @NotBlank(message = "비밀번호 체크를 비워둘 수 없습니다.")
    private String pwdChk;
}
```
<br>

### ▶ 문의하기 게시판

<b>게시글</b>

* 게시글 리스트
   * `/qna`
   * 페이지네이션 적용
   * QuestionResponseDto에 content제거(본인만 상세페이지 확인 가능하므로)
   * 전체 데이터 수와 필요한 페이지 수 함께 리턴
```java
public QuestionResponseDto(Question question, Long qnaSize, int pageSize) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.writer = question.getUser().getNickname();
        this.modified = question.getModified();
        this.qnaSize = qnaSize;
        this.pageSize = pageSize;
    }
```

* 게시글 상세보기
   * `/qna/{qnaId}/detail`
   * 게시글에 연관된 댓글도 함께 리턴
   
* 게시글 작성
  * `/qna`
  * QuestionRequestDto에 `@NotBlank`어노테이션을 이용하여 validation 체크. 비어있을 경우 message리턴
```java
@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private Long userId;
}
```

* 게시글 수정
   * `/qna/{qnaId}`
   * 본인만 수정 가능
   * `@NotBlank` validation체크
   
* 게시글 삭제
   * `/qna/{qnaId}`
   * 본인만 삭제 가능
   * `cascade = CascadeType.REMOVE`으로 게시글에 연관된 댓글 함께 삭제

<b>댓글</b>

 * 게시글과 `@ManyToOne` mapping
 * configure에 다음 조건 추가하여 관리자만 접근 가능
   ```java
   .antMatchers(HttpMethod.POST,"/qcomment/**").hasRole("ADMIN")
   .antMatchers(HttpMethod.PUT,"/qcomment/**").hasRole("ADMIN")
   .antMatchers(HttpMethod.DELETE,"/qcomment/**").hasRole("ADMIN")
   ```
 * service에서 role 한번 더 검증
 * 댓글 작성 : `/qcomment/{qnaId}`
 * 댓글 수정 :  `/qcomment/{qcommentId}/qna/{qnaId}`   
 * 댓글 삭제 : `/qcomment/{qcommentId}/qna/{qnaId}`

<br>
<br>

### Reference

http://yoonbumtae.com/?p=3000



