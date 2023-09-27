## Travel Planner.

---

🕓 개발 기간 : 2023.06.18 ~ 현재

👩‍👧‍👦 팀 구성 : 프론트 `3`, 백엔드 `3` 

### Links.

---

- Github:  👉🏻 [travel-planner-project/TravelPlanner (github.com)](https://github.com/travel-planner-project/TravelPlanner)
- 운영
    
    > Server : 👉🏻
    Swagger : 👉🏻 https://travel-planner.xyz/swagger-ui/index.html
    > 
- 배포
    
    > Server : 👉🏻
    Swagger : 👉🏻  https://dev.travel-planner.xyz/swagger-ui/index.html
    > 
- Notion
    
    > Team : 👉🏻 [https://sieun96.notion.site/72671c3f13344a40a41dd0fd0901addf?pvs=4](https://www.notion.so/72671c3f13344a40a41dd0fd0901addf?pvs=21)
    Backend WIKI : 👉🏻 [https://sieun96.notion.site/bf6ac20970fa495885e6323dfc9e66d4?pvs=4](https://www.notion.so/bf6ac20970fa495885e6323dfc9e66d4?pvs=21)
    > 
    

### Teams.

밑에 메일이나 깃헙 주소를 넣을까여? 아님 한 일을 정리해서 넣을까여?

- 이름에 깃헙 주소 링크를 달고, 한 일을 아래에 같이 정리하면 좋을 듯 합니당!
- 굳굳

---

Backend

김시은

Backend

신세인

Backend

임준형

Front

박설화

Front

최예슬

Front

하종승

### About Project.

---

**`Travel Planner`**는 친구들과 실시간으로 여행을 계획할 수 있는 웹 애플리케이션입니다.

친구들과 실시간으로 채팅을 하며, 직접 만나지 않고도 Travel Planner를 통해 마치 함께 있는 것처럼 여행 계획을 짤 수 있습니다, 

또한 SNS 및 커뮤니티 기능을 통해 자신의 여행을 사진과 글로 기록하고 공유하며 친구들과 소통할 수 있습니다.

여행을 떠나고 싶다면 Travel Planner와 함께 떠나요 ~😎✈️

### Stacks.

---

<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">

[Simple Icons](https://simpleicons.org/?q=github) 참고하여 작성

🛠️ **Frontend**  

🛠️ **Backend**

java, spring boot, websocket, stomp

hibernate, Spring Security, jwt, swagger

OAuth2.0

🛠️ **Server/DB**

mysql, rds, redis

AWS, Nginx

🛠️ **Tools**

git, github

intellij, postman

websocketDevTools

🛠️ **Communication**

notion, figma, discord

### Layout.

---

- 메인페이지
- 로그인 / 회원가입 페이지
- 플래너 페이지
    - 플래너 생성
    - 친구 추가
- 채팅 페이지
- SNS 페이지
- 커뮤니티 페이지

### Main Function.

---

⚙️ 로그인 / 회원 가입

- JWT Token(Access/Refresh)을 사용하여 로그인 진행.
- OAuth2.0을 사용하여 소셜 로그인 진행.
- 비밀번호 변경 시 임시 비밀번호를 해당 이메일로 전송.

⚙️ 플래너 및 채팅 기능

- Websocket을 적용하여 그룹 멤버와 함께 즉각적으로 여행 계획 공유.
- Websocket을 적용하여 그룹 멤버와 함께 실시간 채팅.
- 이메일 검색을 통한 그룹 멤버 추가 및 삭제.
- 날짜 별 장소와 할 일 기록.

⚙️ SNS 기능

- 사진 / 글을 기록 및 공유.
- 댓글 및 대댓글 기능.

⚙️ 커뮤니티 기능

### Architecture.

---

![제목 없는 다이어그램-페이지-2.drawio.svg](https://prod-files-secure.s3.us-west-2.amazonaws.com/362810da-0f97-409b-9071-01de43102871/78d7df0c-d907-4263-8fa4-2d46cdf40409/%EC%A0%9C%EB%AA%A9_%EC%97%86%EB%8A%94_%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8-%ED%8E%98%EC%9D%B4%EC%A7%80-2.drawio.svg)


### ERD.

---

- 1차 배포

![entityManagerFactory(EntityManagerFactoryBuilder, PersistenceManagedTypes).png](https://prod-files-secure.s3.us-west-2.amazonaws.com/362810da-0f97-409b-9071-01de43102871/c5ad0a7d-d438-40e4-8a17-a2805a17922f/entityManagerFactory(EntityManagerFactoryBuilder_PersistenceManagedTypes).png)

- 2차 배포

![ㄴ.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/362810da-0f97-409b-9071-01de43102871/62678e99-e4e7-41a0-8102-36b505d9b0d2/%E3%84%B4.png)
