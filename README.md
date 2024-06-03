# 프로젝트 소개

### 프로젝트 이름

Pet Partner

- 프로젝트 개발 기간 : 2024.05.27~2024.06.03
- 개발 언어 : Kotlin, Spring
  
### 팀원
<table>
    <tr>
      <td align="center"><a href="https://github.com/DEVxMOON"><sub><b>팀장 : 정혜린</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/gooddle"><sub><b>팀원 : 이지원</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/ckhcree"><sub><b>팀원 : 김보현</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/npureaun"><sub><b>팀원 : 권지훈</b></sub></a><br /></td>
</table>

### 개요

반려동물을 키우는 사람들을 위한 정보 공유의 장!

내가 키우는 반려동물과의 일상을 공유하고 정보를 주고 받으며,

도움이 필요한 동물을 위한 입양정보도 받아보아요!

### 주요 기능

- 필수 구현 + 선택 구현
    - 게시물 CRUD 기능
    - 뉴스 피드 기능 (메인 페이지 / 전체 조회 페이지)
    - 상세 보기 기능
    - 댓글 CRUD 기능
    - 사용자 인증/인가 기능
        - 회원가입
            - 이메일 가입 및 인증 기능
            - 회원가입시 받아야하는 정보 중 이메일을 추가하고 사용자 인증을 받는다.
        - 로그인 및 로그아웃
        - 인가
    - 프로필 관리
        - 프로필 수정
            - 이름, 한줄소개 등 기본적인 정보를 볼 수 있고, 수정할수 있어야한다.
            - 비밀번호 수정시, 수정 전 비밀번호를 받고 확인하는 과정이 필요하다.
    - 좋아요 기능
        - 좋아요를 남기거나 취소 할 수 있어야 한다.
        - 본인의 게시글에는 좋아요 불가능.

## 와이어 프레임
![image](https://github.com/5-SpringSpring/Pet-Partner/assets/137713546/523f6201-cc8f-4175-9bad-281ac5897e15)


## ERD
![image](https://github.com/5-SpringSpring/Pet-Partner/assets/137713546/80008591-0677-4b04-b5db-087f7d08200e)


## API 명세서
### FEED API 명세

| Command                     | API Path          | Method | Response |
|-----------------------------|-------------------|--------|----------|
| **게시글 작성**             | `/feeds`          | POST   | 201      |
| **게시글 전체 조회**        | `/feeds`          | GET    | 200      |
| **작성자 기준으로 게시물 조회** | `/feeds/username`  | POST   | 200      |
| **카테고리로 게시물 조회**  | `/feeds/categories` | GET    | 200      |
| **게시글 상세 조회**        | `/feeds/{feedId}` | GET    | 200      |
| **게시글 수정**             | `/feeds/{feedId}` | POST   | 200      |
| **게시글 삭제**             | `/feeds/{feedId}` | DELETE | 204      |


### LOVE API 명세

| Command                      | API Path                      | Method | Response |
|------------------------------|-------------------------------|--------|----------|
| **게시글 좋아요**            | `/feeds/{feedId}/loves/update` | POST   | 201      |
| **게시글 좋아요 취소**       | `/feeds/{feedId}/loves/update` | POST   | 204      |
| **좋아요 상세 보기**         | `/feeds/{feedId}/loves/detail` | GET    | 200      |
| **user가 좋아요 누른 피드 조회** | `/feeds/loves`                 | POST   | 200      |


### COMMENT API 명세

| Command     | API Path                         | Method | Response |
|-------------|----------------------------------|--------|----------|
| **댓글 생성** | `/feeds/{feedId}/comments`        | POST   | 201      |
| **댓글 수정** | `/feeds/{feedId}/comments/{commentId}` | PUT    | 200      |
| **댓글 삭제** | `/feeds/{feedId}/comments/{commentId}` | DELETE | 204      |


### USER API 명세

| Command            | API Path                  | Method | Response |
|--------------------|---------------------------|--------|----------|
| **회원가입**       | `/users/signup`           | POST   | 201      |
| **로그인**         | `/users/login`            | POST   | 200      |
| **회원 정보**      | `/users/userinfo`         | POST   | 200      |
| **비밀번호 변경**  | `/users/update-password`  | PATCH  | 200      |
| **이름 변경**      | `/users/update-username`  | PATCH  | 200      |
| **로그아웃**       | `/users/logout`           | DELETE | 200      |


## 패키지 구조

```kotlin

  src
    └─main
        ├─kotlin
        │  └─team
        │      └─springpsring
        │          └─petpartner
        │              ├─domain
        │              │  ├─exception
        │              │  │  └─dto
        │              │  ├─feed
        │              │  │  ├─comment
        │              │  │  │  ├─controller
        │              │  │  │  ├─dto
        │              │  │  │  ├─entity
        │              │  │  │  ├─repository
        │              │  │  │  └─service
        │              │  │  ├─controller
        │              │  │  ├─dto
        │              │  │  ├─entity
        │              │  │  ├─repository
        │              │  │  └─service
        │              │  ├─love
        │              │  │  ├─controller
        │              │  │  ├─dto
        │              │  │  ├─entity
        │              │  │  ├─repository
        │              │  │  └─service
        │              │  ├─security
        │              │  │  ├─hash
        │              │  │  └─jwt
        │              │  └─user
        │              │      ├─controller
        │              │      ├─dto
        │              │      ├─entity
        │              │      ├─loginUser
        │              │      │  ├─entity
        │              │      │  ├─repository
        │              │      │  └─service
        │              │      ├─repository
        │              │      ├─service
        │              │      └─validemail
        │              │          ├─controller
        │              │          ├─dto
        │              │          └─service
        │              └─infra
        │                  └─swagger
        └─resources
```

## 데모 영상
https://youtu.be/X3psA6ZDDgQ
---

# 협업 전략

## Commit 전략

```kotlin
Feat : 기능 추가
```

| Feat | 새로운 기능 추가 |
| --- | --- |
| Fix | 버그 수정 |
| !HOTFIX | 급하게 치명적인 버그를 고쳐야하는경우 |
| Docs | 문서 수정 |
| Style | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우 |
| Refactor | 프로덕션 코드 리팩토링 |
| Test | 테스트 추가, 테스트 리팩토링(프로덕션 코드 X) |
| Merge | 브랜치를 머지한다 |
| Remove | 파일을 삭제하는 작업만 수행한 경우 |
| Rename | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우 |
| gitignore | 깃이그노어 파일 관리 |
| init | 프로젝트 생성 |

## 브랜치 전략

```kotlin
속성#이슈번호 - 브랜치 명
```

main(배포용) - 프로젝트 끝날때, 마지막에 최종적으로 합칠 때 머지할 것.

- dev 브랜치 안에 속성을 활용해 브랜치 만들기.
    - dev/feat#1-브랜치이름
    - dev/fix#4-브랜치이름

| 속성 | 설명 |
| --- | --- |
| feat | 새로운 기능을 구현할 때 사용해요. |
| refactor | 기능은 똑같이 작동하지만, 코드를 리팩토링할 때 사용해요. |
| fix | 에러를 수정할 때 사용해요. |

### 브랜치 보호전략

main브랜치

- 최종 코드 전원 확인 후 팀장만 merge 가능

dev 브랜치

- 본인 이외 1명 이상의 팀원의 approve와 코드리뷰가 있어야지만 merge가능.
