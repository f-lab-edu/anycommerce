# anycommerce
## 이커머스 프로젝트

1. 회원 가입 페이지 만들어보기 (2024-11-11 ~ ) 
2. 컨트롤러 정의해보기(2024-11-18 ~ )
3. 6개 API정의하고 Controller 다시 작성 (2024-11-25 ~ )
4. 약관과 약관동의 엔티티에 복합키 적용, 불필요한 코드 삭제, 외부 SMS API 연동, 이상한 로직들 수정, 코드에 대한 피드백 적용. (2024-12-02 ~ )
5. 에러코드 적용 및 4주차 못했던 작업들 보완 (2024-12-09 ~ )
6. 회원가입 개발 종료 -> 토큰 방식 로그인 구현 (2024-12-16 ~ )
7. Swagger 만들기 (2024-12-22 ~ )
8. 메인페이지 구성 (2024-12-30 ~ )
9. 메인페이지 json response 만들어보고 다시 보완하기 + Integration Test 해보기 (2025-01-06 ~ )
10. Integration Test 구현 (2025-01-13 ~)
11. 상품 상세, 장바구니, 주문 (2025-01-20 ~ )
12. 주문 마무리 및 CI-CD 구축해보기 (2025-02-03 ~ ) (최종목표: AWS ECR 배포 )
13. 프로젝트 종료
* 프로젝트 docker 실행 시 필요한 것
- .env에 필요한 환경변수:
---
 
 MYSQL_ROOT_PASSWORD
 MYSQL_USER
 MYSQL_PASSWORD
 MYSQL_DATABASE
 SPRING_DATASOURCE_URL
 JWT_SECRET_KEY
 COOLSMS_API_KEY
 COOLSMS_API_SECRET
 ENCRYPTION_KEY
 AWS_ACCESS_KEY
 AWS_SECRET_ACCESS_KEY
 ECR_REGISTRY
 
---
  * coolsms api 키
  * encryption key - 비밀번호 암호화에 필요한 요소
  * jwt_secret_key - jwt 발급할때 필요한 시크릿 키 (사용자가 임의로 만들면 된다. 32bit이므로 32자 권고)
- Docker 필요
- ECR 연결
- ECR 필요한 작업 엑세스 키 , 엑세스 비밀 키, 초기 토큰 인증(AWS CLI)
- 그냥 로컬에서 실행 시(ECR pull 안하고 단순 구동) docker-compose.overrated.yml 유지, 필요 없을 시 삭제.
---
3번 작업들
 - 약관(Title, 회원가입 페이지에서 보이는 글)을 서버로부터 가져오는 것
 - 약관 상세 보기
 - Email 중복 체크
 - 휴대폰 인증번호 발송
 - 휴대폰 인증번호 검증
 - 최종 회원가입 
---
3. 추가 작업

  - 위 항목 Controller와 연계될 Entity 작성 및 기존 Code 수정
  - 연계될 Service Code 작성
  - 외부 SMS API 발송 연계 
---
