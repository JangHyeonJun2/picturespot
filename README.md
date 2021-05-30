# SFlash - 지도 SNS 서비스 📸✨
[![bg-3.png](https://i.postimg.cc/rsYZfRbT/bg-3.png)](https://postimg.cc/V0nF6vmZ)
<br>

<p align="center">
  <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/HTML-E34F26?style=flat-square&logo=html5&logoColor=white"/>
  <img src="https://img.shields.io/badge/CSS-1572B6?style=flat-square&logo=css3&logoColor=white"/>
  <img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=react&logoColor=white"/><br>
  <img src="https://img.shields.io/badge/Json_Web_Tokens-000000?style=flat-square&logo=Json-Web-Tokens&logoColor=white"/>
  <img src="https://img.shields.io/badge/AmazonS3-569A31?style=flat-square&logo=amazon-s3&logoColor=white"/>
  <img src="https://img.shields.io/badge/aws-232F3E?style=flat-square&logo=amazon-aws&logoColor=white"/>
  <img src="https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=MariaDB&logoColor=white"/>
  <img src="https://img.shields.io/badge/Travis_CI-3EAAAF?style=flat-square&logo=Travis-CI&logoColor=white"/>
  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/>
  
</p>

# [🏠 HOME PAGE](https://www.sflash.net/)<br>
## 💡 프로젝트 소개
사람들은 알고 나만 모르는 스팟들…
대체 거기가 어디야? 블로그, 인스타 검색 이제 그만!!
SFlash(Spot + Flash)는 전국의 명소들을 사진과 지도로 한눈에 볼수 있도록 기획한 서비스 입니다.
<br>

#### 👨‍👨‍👦‍👦 팀원
* Frontend : 허민규, 김다영, 김형민 `React`
* Backend : 장현준, 김승욱, 이세정 `SpringBoot`
* Designer : 송은정, 임아현

#### 📅 진행 기간
* 21.04.23(금) - 21.05.28(금)

#### ✍ 기획 배경
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
* File Storage: `AWS S3 Bucket`
* CI/CD : `Travis`, `codedeploy`,`s3`
<br>

## 💡 전체 구조
![](https://user-images.githubusercontent.com/55679927/119793545-a778e780-bf11-11eb-946e-581fd2063913.jpeg)


## 💡 주요 기능



| [로그인/회원가입](https://github.com/JangHyeonJun2/picturespot/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EC%86%8C%EA%B0%9C#-sflash-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85-%EB%A1%9C%EA%B7%B8%EC%9D%B8-jwt-%EC%A0%95%EB%A6%AC) | [이메일/비밀번호 찾기](https://github.com/JangHyeonJun2/picturespot/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EC%86%8C%EA%B0%9C#-%EC%9D%B4%EB%A9%94%EC%9D%BC%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EC%B0%BE%EA%B8%B0) | [소셜 로그인](https://github.com/JangHyeonJun2/picturespot/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EC%86%8C%EA%B0%9C#-oauth2-%EC%86%8C%EC%85%9C%EB%A1%9C%EA%B7%B8%EC%9D%B8) | 게시글 CRUD                                                  | 다중 이미지 업로드                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [![2021-05-31-4-54-56.png](https://i.postimg.cc/qBspRJK7/2021-05-31-4-54-56.png )](https://postimg.cc/gr2CtPN9) | [![2021-05-31-4-56-35.png](https://i.postimg.cc/DwD96HGD/2021-05-31-4-56-35.png )](https://postimg.cc/G4FghMqJ) | ![2021-05-31-4-54-56.png](https://i.postimg.cc/qBspRJK7/2021-05-31-4-54-56.png ) | [![2021-05-31-4-58-55.png](https://i.postimg.cc/qq3ht98T/2021-05-31-4-58-55.png)](https://postimg.cc/N2QfSCJJ) | [![2021-05-31-5-00-07.png](https://i.postimg.cc/g2DssB7p/2021-05-31-5-00-07.png )](https://postimg.cc/Wdd0bSjY) |
| **댓글CRUD**                                                 | **좋아요**                                                   | **프로필 편집**                                              | [**문의하기 CRUD**](https://github.com/JangHyeonJun2/picturespot/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EC%86%8C%EA%B0%9C#-%EB%AC%B8%EC%9D%98%ED%95%98%EA%B8%B0-%EA%B2%8C%EC%8B%9C%ED%8C%90) | [**문의하기 답변 CRUD**](https://github.com/JangHyeonJun2/picturespot/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EC%86%8C%EA%B0%9C#%EB%8C%93%EA%B8%80) |
| [![2021-05-31-5-01-11.png](https://i.postimg.cc/vZ37fx1H/2021-05-31-5-01-11.png )](https://postimg.cc/3k0DHRCP) | [![2021-05-31-5-02-05.png](https://i.postimg.cc/tTPVSsg6/2021-05-31-5-02-05.png )](https://postimg.cc/r0VmzFxy) | [![2021-05-31-5-02-55.png](https://i.postimg.cc/hGQJvXTY/2021-05-31-5-02-55.png)](https://postimg.cc/Y4k2yqXN) | [![2021-05-31-5-03-42.png](https://i.postimg.cc/6pg3kBgf/2021-05-31-5-03-42.png )](https://postimg.cc/0zGPm18z) | [![2021-05-31-5-04-38.png](https://i.postimg.cc/SRSN6RYk/2021-05-31-5-04-38.png )](https://postimg.cc/BLz0sqCV) |






<br>





