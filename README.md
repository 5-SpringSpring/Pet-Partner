 API 명세
 # FEED API
| Command     | API Path  | Method | Response |
|--------- | -------------| -------------| -------------|
| 게시글 작성 | /feeds | POST | 201 |
| 게시글 전체 조회 | /feeds |  GET | 200 |
| 작성자 기준으로 게시물 조회 | /feeds/username | POST | 200 |
| 카테고리로 게시물 조회 | /feeds/categories | GET | 200 |
| 게시글 상세 조회 | /feeds/{feedId} | GET | 200 |
| 게시글 수정 | /feeds/{feedId} | POST | 200 |
| 게시글 삭제 | /feeds/{feedId} |  DELETE | 204 |

# LOVE API
| Command     | API Path  | Method | Response |
|--------- | -------------| -------------| -------------|
| 게시글 좋아요 | /feeds/{feedId}/loves/update | POST | 201 |
| 게시글 좋아요 취소 | /feeds/{feedId}/loves/update |  POST | 204 |
| 좋아요 상세 보기 | /feeds/{feedId}/loves/detail | GET | 200 |
| user가 좋아요 누른 피드 조회 | /feeds/loves | POST | 200 |


# COMMENT API
| Command     | API Path  | Method | Response |
|--------- | -------------| -------------| -------------|
| 댓글 생성 | /feeds/{feedId}/comments | POST | 201 |
| 댓글 수정 | /feeds/{feedId}/comments/{commentId} | PUT | 200 |
| 댓글 삭제 | /feeds/{feedId}/comments/{commentId} | DELETE | 204 |


# USER API
| Command     | API Path  | Method | Response |
|--------- | -------------| -------------| -------------|
| 회원가입 | /users/signup | POST | 201 |
| 로그인 | /users/login |  POST | 200 |
| 회원 정보 | /users/userinfo | POST | 200 |
| 비밀번호 변경 | /users/update-password | PATCH | 200 |
| 이름 변경 | /users/update-username | PATCH | 200 |
| 로그아웃 | /users/logout} | DELETE | 200 |
