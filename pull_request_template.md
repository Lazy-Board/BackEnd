
## Motivation (작업내역)
* example
  * 객체들의 연관관계를 기준으로 엔티티를 초기 설계했습니다.
  * 엔티티 설계 후 Embedded H2로 테이블을 생성했을 때, MySQL 테이블과 동일한 형태를 얻고자 했습니다.


## Key Changes (작업 상세내용)
* example
  * 생성된 H2의 테이블입니다.

![image](https://user-images.githubusercontent.com/110509654/216828670-8460a3bd-5b7c-466f-ad3a-e53787afc26a.png)
* example
  * 정해져 있는 상수값을 지키는 String 필드의 경우 Enum으로 선언했습니다.


## To Reviewers (리뷰내용 작성)
* example
  * MySQL 테이블과 비교하면서 검토하고 리뷰해주세요
