## Lazier Project

# Lazier - 맞춤형 정보 조회 서비스

## 📜 기술 블로그
* [**Lazier(notion)**](https://www.notion.so/Lazier-10dfb232b0124c2887771d9f98a9d090)

## 🙆‍♂️ 팀원 소개

|![image](https://user-images.githubusercontent.com/110509654/227474208-7a5aeda4-d83d-435d-8eb7-8fe3aeb82edb.png)|![image](https://user-images.githubusercontent.com/110509654/227474343-a0324a3b-c19e-4908-9351-b755d482a8e8.png)|![image](https://user-images.githubusercontent.com/110509654/227474463-cbd8443e-2e94-497e-aaba-bf3daae21b4b.png)|![image](https://user-images.githubusercontent.com/110509654/227474545-cb17144c-b76a-4dac-a416-8284558d787e.png)|![image](https://user-images.githubusercontent.com/110509654/227474749-058806c4-6001-4725-829a-9b73c9776de1.png)|![image](https://user-images.githubusercontent.com/110509654/227474832-e3443fed-3ca3-4487-8edd-ce0f92e112e2.png)|
|:---:|:---:|:---:|:---:|:---:|:---:|
|**Front-End**|**Front-End**|**Back-End**|**Back-End**|**Back-End**|**Back-End**|
|**김태영**|**장지연**|**👑하민성👑**|**서인덕**|**윤윤성**|**이혜린**|


## 👨‍💻 프로젝트 소개

**🎇 Slogan - 바쁜 현대사회에서 조금 더 게으른 당신의 삶을 위해**

![lazy](https://user-images.githubusercontent.com/110509654/226874078-6931caa1-1e2c-40ba-9781-b256ababb055.gif)

**❓ 기획 배경**

![image](https://user-images.githubusercontent.com/110509654/226874233-6710d6f2-6977-4703-8dfe-7bb47b52c043.png)

* 계좌 정보를 확인하려면 은행 사이트를, 뉴스를 보려면 신문사 뉴스 사이트/어플을 방문해야 한다.
  * 하지만 해당 어플리케이션에서 보는 기능은 제한적이다. 주로 단순한 정보를 이용한다. ('오늘 날씨', '오늘 할 일', '지금 뉴스')
* 관심있는 나의 정보와 일정 등을 간편하고 한눈에 파악할 수 있는 웹 어플리케이션을 생각하게 되었다.

**☝️ 해결 컨셉 : Easy One For All**
1) 나에게 필요한 정보만 **골라서** **직관적**으로 편하게 보자
2) 간편하게 **모바일**로 볼 수 있는 웹 어플리케이션

**👨 목표: 기대 효과(USER)**
1) 여러 포털 사이트 / 앱을 사용하지 않아도 원하는 정보를 **한 페이지에서** 확인 할 수 있다.
2) **원하지 않는 정보는 보지 않도록** **모듈화된 기능을 선택**하여 나만의 입맛에 맞는 페이지를 구성할 수 있다.

**=> 최종 총 8개의 기능모듈 + 메인모듈로 구성된 모바일 웹 어플리케이션 구상.**


## 🤝 협업 과정

**🧭 Ground Rule**

**Ground Rule = Growth Rule**

> Rule : 모든 원칙은 우리의 성장과 공동의 목표 달성을 위해 존재합니다.

![image](https://user-images.githubusercontent.com/110509654/227481240-c88e37ea-ec5f-4519-beee-e207f09407ed.png)

* 스크럼 : 매일 노션 양식에 작성
* 코드리뷰 : 주 2회 (요일 미정) / 개발기간은 백엔드, 프론트 파트별로 진행
* 회의시간 : 월~금 / 일요일 오후 10시
* 특이사항 : 매주 토요일은 회의 X, 텍스트로 작업내용 공유

**💼 Work Time**

![image](https://user-images.githubusercontent.com/110509654/227482173-8cc566b7-769c-42dc-a929-349d9285ed33.png)

**✔ Daily Scrum : 정규 스크럼 주 6일 + @**

![image](https://user-images.githubusercontent.com/110509654/227482417-dfe37a09-e086-4723-8cda-ac249d7affe9.png)

**👨‍👨‍👧‍👧 Google Meet - 미팅: Daily Scrum**

![image](https://user-images.githubusercontent.com/110509654/227482802-020a34d5-0cc9-41ac-aac3-0028a17d7285.png)

**🤼‍♀️ Gather - 협업공간: 수시 회의/협업**

![image](https://user-images.githubusercontent.com/110509654/227482872-52d26f02-2fe0-43a9-9cfd-c61d485a8f27.png)

**🔑 Git 전략: Branch & PR**

![image](https://user-images.githubusercontent.com/110509654/227488630-8fd1bcd7-9e4c-492c-9856-d6ee53d21443.png)


**1. Repository / Branch Manangement**
* 본 프로젝트는 메인 + 기능별 8개, 총 9개 모듈로 구성. 프론트 엔드/백엔드 서버 를 분리하여 레포지터리 구성.
* 각 담당자는 _담당모듈 레포지터리에 커밋/푸쉬할 베이스 프로젝트 생성/셋팅 후 터미널에서 아래의 커맨드로 초기 브랜치 셋팅 후 first commit._
  * main : 최종 배포를 위한 브랜치 (**master는 사용하지 않는다)
  * dev : 개발을 위한 브랜치 (모든 개발내용은 해당 브랜치위에서 feat단위로 서브 브랜치 생성/commit 후 해당 브랜치로 Merge한다.
  * test : 배포 전 테스트 서버 구동을 위한 브랜치.
  
**2. PR Strategy**
* Release(Dev Branch로 Merge)는 전원의 감수 및 동의로 진행한다.
* HotFix는 과반수(2명) 이상의 동의를 받는다.
* 모든 파트원(Back : 4 / Front : 2)에게 리뷰(확인)를 받아야 Release를 진행할 수 있다.

## 👨‍🔧 기술 스택

**Front-End**

<img src="https://img.shields.io/badge/React-000000?style=flat-square&logo=React&logoColor=#61DAFB"/></a>
<img src="https://img.shields.io/badge/TypeScript-000000?style=flat-square&logo=TypeScript&logoColor=#3178C6"/></a>
<img src="https://img.shields.io/badge/React Router v6-000000?style=flat-square&logo=React Router&logoColor=#CA4245"/></a>
<img src="https://img.shields.io/badge/Tailwind CSS-000000?style=flat-square&logo=Tailwind css&logoColor=#06B6D4"/></a>
<img src="https://img.shields.io/badge/styled components-000000?style=flat-square&logo=styled-components&logoColor=#DB7093"/></a>
<img src="https://img.shields.io/badge/React Query-000000?style=flat-square&logo=React Query&logoColor=#FF4154"/></a>
<img src="https://img.shields.io/badge/Axios-000000?style=flat-square&logo=Axios&logoColor=#5A29E4"/></a>
<img src="https://img.shields.io/badge/Figma-000000?style=flat-square&logo=Figma&logoColor=#F24E1E"/></a>
<img src="https://img.shields.io/badge/Recoil-000000?style=flat-square&logo=Recoil&logoColor=#DB7093"/></a>


**Back-End**

<img src="https://img.shields.io/badge/Spring Boot-000000?style=flat-square&logo=Spring Boot&logoColor=#6DB33F"/></a>
<img src="https://img.shields.io/badge/Gradle-000000?style=flat-square&logo=Gradle&logoColor=#02303A"/></a>
<img src="https://img.shields.io/badge/Spring Security-000000?style=flat-square&logo=Spring Security&logoColor=#6DB33F"/></a>
<img src="https://img.shields.io/badge/Spring JPA-000000?style=flat-square&logo=Spring Jpa&logoColor=#6DB33F"/></a>
<img src="https://img.shields.io/badge/Spring Batch-000000?style=flat-square&logo=Spring Batch&logoColor=#6DB33F"/></a>
<img src="https://img.shields.io/badge/QueryDsl-000000?style=flat-square&logo=QueryDsl&logoColor=#02303A"/></a>
<img src="https://img.shields.io/badge/Oauth 2.0-000000?style=flat-square&logo=Authy&logoColor=#EC1C24"/></a>
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=flat-square&logo=JSON Web Tokens&logoColor=#000000"/></a>

<img src="https://img.shields.io/badge/MariaDB-000066?style=flat-square&logo=MariaDB&logoColor=#003545"/></a>
<img src="https://img.shields.io/badge/Redis-111111?style=flat-square&logo=Redis&logoColor=#DC382D"/></a>
<img src="https://img.shields.io/badge/H2-111111?style=flat-square&logo=H2&logoColor=#DC382D"/></a>

**Production & Deploy**

<img src="https://img.shields.io/badge/AWS-000033?style=flat-square&logo=Amazon AWS&logoColor=#232F3E"/></a>
<img src="https://img.shields.io/badge/Amazon EC2-000033?style=flat-square&logo=Amazon EC2&logoColor=#FF9900"/></a>
<img src="https://img.shields.io/badge/Amazon RDS-000033?style=flat-square&logo=Amazon RDS&logoColor=#527FFF"/></a>
<img src="https://img.shields.io/badge/Amazon S3-000033?style=flat-square&logo=Amazon S3&logoColor=#569A31"/></a>
<img src="https://img.shields.io/badge/Docker-000033?style=flat-square&logo=Docker&logoColor=#2496ED"/></a>
<img src="https://img.shields.io/badge/Jenkins-333333?style=flat-square&logo=Jenkins&logoColor=#D24939"/></a>

**Collaboration tool**

<img src="https://img.shields.io/badge/Slack-660099?style=flat-square&logo=Slack&logoColor=#4A154B"/></a>
<img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=#000000"/></a>

## ⚙️ Architecture

![image](https://user-images.githubusercontent.com/110509654/227493034-c3de389e-1995-4075-8e82-c1f85f196041.png)


## 📑 ERD 

![image](https://user-images.githubusercontent.com/110509654/227492926-baf1625c-62e1-4ec9-a9ab-7160f3f07f32.png)


## 📱 Demo

|회원 기능|뉴스|
|:---:|:---:|
|![회원가입, 일반로그인, 소셜로그인, 비밀번호 찾기](https://user-images.githubusercontent.com/110509654/227857747-784e0803-9a3f-46ea-b688-2ac732cdc23c.gif)|![뉴스 모듈](https://user-images.githubusercontent.com/110509654/227857777-e72d2005-ff52-44c8-acb1-e3f6ef023d54.gif)|

|날씨|출근정보|
|:---:|:---:|
|![날씨 모듈](https://user-images.githubusercontent.com/110509654/227858057-0efcb224-8101-4566-bac6-1bbd43237c20.gif)|![출근정보](https://user-images.githubusercontent.com/110509654/227858084-978326e2-628a-4fbf-aa33-e794d03c4adc.gif)|

|유튜브, 오늘의 명언, TODO 리스트|주식|
|:---:|:---:|
|![유튜브, 오늘의 명언, 투두 리스트](https://user-images.githubusercontent.com/110509654/227858107-9ea9f258-f098-4081-aa9d-150aed9da2f6.gif)|![주식](https://user-images.githubusercontent.com/110509654/227858024-d404f554-f139-4285-bb18-72cd92dfa71b.gif)|

|환율|
|:---:|
|![환율](https://user-images.githubusercontent.com/110509654/228519779-6d5e4492-6a29-4e52-92c7-b3b14629fa37.gif)|

## ✨ 프로젝트 주요기능

### 🔐 회원 기능

**🔑 회원가입 및 로그인, 비밀번호 찾기**
- 회원가입
    - 유효한 이메일 주소가 있으면 회원가입이 가능하다.
    - 이메일 인증을 시도한다.
- 로그인
    - 회원가입을 통한 로그인과 `OAuth`를 통한 간편 로그인이 있다.
    - 모두 **토큰 기반의 로그인**으로 만료 시간이 지나면 토큰을 재발급하여 로그인 상태를 유지한다.
- 비밀번호 찾기
    - 등록된 이메일과 연락처를 통해 비밀번호를 초기화할 수 있다.
    - 이메일과 연락처를 확인해서 회원의 이메일로 비밀번호 초기화 링크가 전송된다.


**🗂️ 모듈 저장, 수정, 조회**

|모듈 저장, 수정, 조회|
|:---:|
|![모듈등록 (2)](https://user-images.githubusercontent.com/110509654/227859877-6e1f5a46-c776-4f72-b4ba-78ba74c21129.gif)|

- 모듈 저장
    - 처음 로그인할 시 보고 싶은 모듈을 체크할 수 있다.
- 모듈 조회 및 수정
    - 메인 페이지에서 보고 싶은 모듈을 조회 및 업데이트한다.

**💁‍♂️ 회원 수정,  🔏 비밀번호 변경 , 🤦🏻‍♀️ 회원 탈퇴**

|회원 수정, 비밀번호 변겅, 회원 탈퇴|
|:---:|
|![회원 수정, 비밀번호 변경, 회원 탈퇴](https://user-images.githubusercontent.com/110509654/227860781-c6c5f547-d779-486e-ade6-061a69f17b75.gif)|

- 회원 수정
    - 회원가입 시 입력한 정보를 조회하고 수정할 수 있다.
    - 소셜 로그인 사용자는 비밀번호를 제외한 정보를 수정할 수 있다.
    - 프로필 사진을 등록, 수정, 삭제할 수 있다.
- 비밀번호 변경
    - 일반 로그인 사용자는 비밀번호 변경을 이용할 수 있다.
    - `OAuth` 로그인 사용자는 이용할 수 없다.
- 회원 탈퇴
    - 탈퇴 후에는 탈퇴한 사용자 계정으로 로그인할 수 없다.

### 🖥️ 모듈 기능

**📰 뉴스**
- 각 언론사별 주요뉴스를 볼 수 있는 모듈이다.
- 언론사는 총 **61개**의 언론사 정보를 가지고 있다.
- 해당 모듈 최초 등록시 사용자의 정보는 기본값으로 설정되어 메인 페이지에서 **기본으로 설정**된 ‘연합뉴스’의 주요뉴스들을 **최근 순으로 볼 수 있다.**
- 사용자는 메인에서 보고 싶은 언론사를 **1개** 선택 할 수 있고, 그 외 나머지 언론사의 뉴스 정보는 상세 페이지에서 확인 가능하다.
 (메인에서 볼 수 있는 주식 정보 선택은 수정이 가능하다.)
- 사용 기술
    - `BackEnd`
        - JSoup을 이용하여 주식 정보를 Scraping 했다.
        - Spring Batch를 적용하여 1시간 마다 1번씩 뉴스 기사를 저장하고, 하루에 1번 오래된 뉴스 정보를 삭제 및 언론사 정보를 저장하도록 구현하였다.
    - `FrontEnd`
        - Axios를 이용하여 통신하였다.
        - recoil을 이용해 상태값을 저장, 사용하였다.


**📈 주식**
- 주식 정보를 보여주는 모듈이다.
- 주식 정보는 **10개**의 회사 주식 정보를 가진다.
- 해당 모듈 최초 등록하면 **3개** 회사 주식 정보를 메인 페이지에서 볼 수 있다.
- 상세페이지에서 메인화면에 보이는 주식 정보를 수정할 수 있다.
    (상세페이지 : 10개 회사 주식 정보 포함)
- 사용 기술
    - `BackEnd`
        - JSoup을 이용하여 주식 정보를 Scraping 했다.
        - Spring Batch를 적용하여 1시간 마다 1번씩 정보를 삭제후 저장하도록 구현하였다.
    - `FrontEnd`
        - Axios를 이용하여 통신하였다.
        - recoil을 이용해 상태값을 저장, 사용하였다.


**💰 환율**
- 환율 정보를 보여주는 모듈이다.
- 환율정보는 **10개** 국가의 환율 정보를 가진다.
- 해당 모듈 최초 등록하면 **4개** 국가의 환율 정보를 메인 페이지에서 볼 수 있다.
- 상세페이지에서 메인화면에 보이는 환율 정보를 수정할 수 있다.
    (상세페이지 : **10개** 국가의 환율 정보 포함)
- 사용기술
    - `BackEnd`
        - JSoup을 이용하여 환율 정보를 Scraping 했다.
        - Spring Batch를 적용하여 1시간 마다 1번씩 정보를 삭제후 저장하도록 구현하였다.
    - `FrontEnd`
        - Axios, recoil을 사용해 비동기 통신 및 데이터를 저장, 관리했다.
        - 환율 정보 최대 4개까지만 체크 가능하도록 프론트단에서 처리했다.



**🌦️ 날씨**
- 날씨 정보를 알려주는 모듈이다.
- 모듈 등록시 사용자는 지역 정보를 등록해야 날씨 정보를 볼 수 있다.
- 사용자는 **1개**의 지역 정보를 등록 할 수 있다. (지역 정보는 수정이 가능하다.)
- 지역 정보가 잘못될 경우 Error가 발생한다.
- 사용 기술
    - `BackEnd`
        - JSoup을 이용하여 날씨 정보를 Scraping 한다.
        - Spring Batch를 적용하여 1시간 마다 1번씩 정보를 저장하고, 하루에 1번씩 오래된 정보를 삭제하도록 구현하였다.
    - `FrontEnd`
        - Axios, React query, Recoil을 사용해 서버 데이터를 비동기적으로 관리하고 사용다.
        - React-query를 사용해 서버 데이터가 변경될 시 변경된 데이터가 새로고침 없이 fetch 되도록 했다.


**🚗 출근정보**
- 출근길 소요 시간을 알려주는 모듈이다.
- 사용자는 **1개**의 출근길 정보를 등록 할 수 있다. (출발지&도착지 정보는 수정이 가능하다.)
- 주소가 잘못될 경우 Error가 발생한다.
- 사용 기술
    - `BackEnd`
        - Naver API 를 사용해 위경도 정보를 저장한다.
        - Kakao API 를 활용해 출발지 도착지간 소요 시간을 조회한다.
    - `FrontEnd`
        - Axios, React query, Recoil을 사용해 서버 데이터를 비동기적으로 관리하고 사용하였다.
        - KAKAO MAP API를 사용해 카카오맵을 화면에 출력하고, 서버에 보낸 데이터를 받아 도착지 위치를 지도 중앙에 표시하도록 했다.
        - React-query를 사용해 서버 데이터가 변경될 시 변경된 데이터가 새로고침 없이 refetch 되도록 했다.



**▶️ 유튜브 🧙 명언 📝 TODO 리스트**
- 유튜브: 크롤링
    - 실시간 급상승 동영상을 볼 수 있는 모듈이다.
    - 기술 설명 :
        - `BackEnd`
            - JSoup을 활용하여 Scraping 한다.
            - Spring Batch 를 적용하여 1시간 마다 1번씩 정보를 저장하고, 하루에 1번씩 오래된 정보를 삭제하도록 구현하였다.
        - `FrontEnd`
            - Axios를 이용하여 통신하였다.
            - recoil을 이용해 상태값을 저장, 사용하였다.           
- 명언
    - 명언을 볼 수 있고, 사용자가 설정한 개인 문구를 볼 수 있는 모듈이다.
    - 사용자 문구는 최대 1개의 문구만 등록이 가능하다. (문구는 수정 및 삭제가 가능하다.)
    - 명언은 DB에 미리 저장 되어 있어 랜덤으로 조회 하도록 구현하였다.
- TODO 리스트
    - 사용자의 할 일 목록을 보여 줄 수 있는 모듈이다.
    - 사용자는 최대 3개의 할일 목록을 만들 수 있습니다. (목록은 완료 후 추가 가능하다.)


## ☄️ 회고 및 트러블 슈팅

### ○ Front-End

**1. JWT Refresh 토큰**

- CORS 에러 발생
    - 따로 proxy 설정을 해줌.
    - 서버에 요청시 axios interceptor를 사용해 로그인 권한 오류가 발생했을 때, refresh 요청값을 전달해 주는데
    refresh 과정에서 그전에 발급받은(유효기간이 만료된 토큰 값이 들어감 )
    </React.StrictMode>가 렌더링을 두번 요청해서 로컬 스토리지 값이 변동됨.
    - recoile state 에서 토큰값 관리하여 해결
    

**2. Minified React Error 310 오류**

- useRecoilValueLoadable 을 사용해 로딩처리를 해주는 과정에서, 
로딩처리 내용이 react-hook 보다 먼저 선언되어 hooks 선언에 영향을 주는 부분이 있어 생긴 오류였다
로딩처리에 따른 조건부 렌더링으로 해결

**3. GET한 데이터를 filter시 빈 배열로 출력되는 오류**

- object형태 데이터라 Object.entries().filter().map()으로 변환해 filter했으나 AxiosResponse에 필요한 타입이 제대로 지정되어 있지 않아 빈 배열로 출력됨
    - typescript type 설정해 filter된 데이터를 받아 조건부 렌더링함

**4. Minified React Error 426 오류** 

- 데이터 로드하는 동안 UI가 중단되는 상황이 발생해서 생기는 오류
    - useRecoilValue를 useRecoilValueLoadable로 변경해 loading 상태 처리

### ○ Back-End

**1. JWT 토큰**

- CORS 충돌
    - allowedOrigins도 설정했는데도 CORS 충돌이 일어남. OPTION으로 요청이 왔을 때 Filter를 거치지 않도록 하여 해결

**2. AWS S3**

- Url 클릭시 웹에서 열리지 않고 자동으로 다운되는 문제
    - content type을 지정해서 올리지 않으면 자동으로 "application/octet-stream"으로 고정이 되어 자동 다운이 돼서 타입 별로 content type을 지정해서 해결

**3. CI/CD(Jenkins)**

- 도커로 Jenkins 이미지 받고 컨테이너로 실행한 상태에서 홈페이지가 열리지 않는 문제
    - EC2 인스턴스 보안 설정에서 인바운드 규칙 편집 후 해결

**4. Spring Batch**

- 기존에 있던 DB에 배치를 적용하려던 중 Naming 으로 인한 Entity 에러 발생
    - Hibernate naming 을 "PhysicalNamingStrategyStandardImpl"로 적용하고, MariaDB 대소문자 구분 가능하도록 설정되어 있어서 생긴에러.
    ⇒ Hibernate naming 을 "SpringPhysicalNamingStrategy" 로 변경
    - MariaDB 대소문자 구분 안하도록 설정(AWS RDS 파라미터 그룹 메뉴에서 lower_case_table_names를 **1**로 변경

**5. DataBase**
- 로컬 테스트시 사용한 H2 DB에서 발생한 에러
    - ID 값을 GenerationType.IDENTITY 로 설정시 H2 에서 에러 발생
    - H2에서 AUTO_INCREMENT를 사용하기 위해선 IDENTITY 보단 SEQUENCE를 사용한다는 것을 알게 됨(SEQUENCE 로 변경후 정상 작동 확인 완료)
     **=> DB마다 IDENTITY 생성에 사용하는 문법의 차이가 있다.**
     
**6. 크롤링 관련 Issue**

**1) Youtube 스크래핑시 데이터 구조의 문제: 크롤링시 구조가 깔끔하게 되어있지 않은 것도 많다.**

→ 우아하게 스크래핑을 구현하려 했으나 데이터 형식상 Parsing이 어려운 문제로 인해 다중 Json 구조로 10회 정도의 Depth로 파고 들어가야 원하는 정보를 꺼내올 수 있었다.

![image](https://user-images.githubusercontent.com/110509654/227911488-0a9327e7-49f4-48ef-82bd-bd7e2935d192.png)

**2) Bot 차단: 빈번한 페이지 호출로 인해 해당 스크래핑 사이트에서 서버 IP를 차단**

→ 사람으로 인식할 수 있도록 Connection객체의 http헤더 설정 / 크롤링시 일정시간 sleep 등의 프로세스 추가.


## 💡 느낀 점 

### Front-End
Fake Api가 아니라 실제 서버의 데이터를 받아서 적용해보고 여러가지 예상하지 못한 상황과 마주하며 많은 것을 배웠습니다.

### Back-End
개발 업무의 전반적인 과정을 경험할 수 있었고, 많은 소통을 하면서 협업을 해야 좋은 결과물이 나올 수 있다는 것을 느꼈습니다. 많은 에러와 디버깅, 삽질로 채워진 날들이 많았지만 팀원들 모두가 책임을 다해 충실하게 수행해주신 덕분에 무사히 프로젝트를 완성할 수 있었다고 생각합니다.

### 아쉬운 점
다양한 기술을 시도해보고 싶었지만 기획 컨셉과 맞지 않아 제대로 시도해보지 못한 부분이 아쉬운 부분으로 남습니다.


