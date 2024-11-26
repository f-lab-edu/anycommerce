-- Users 테이블 초기 데이터
INSERT INTO users (user_id, password, username, email, phone_number, zipcode, street_address, detail_address, created_at, updated_at)
VALUES
    ('user1', '$2a$10$7sP8JH3rTpKH0.K/ePMrCebxNEmxg95HQaBk58deGQSwQhxO4AwEu', '홍길동', 'user1@example.com', '01012345678', '12345', 'Main Street', 'Apt 101', NOW(), NOW()),
    ('user2', '$2a$10$7sP8JH3rTpKH0.K/ePMrCebxNEmxg95HQaBk58deGQSwQhxO4AwEu', '이순신', 'user2@example.com', '01087654321', '54321', 'Sub Street', 'Apt 202', NOW(), NOW()),
    ('user3', '$2a$10$7sP8JH3rTpKH0.K/ePMrCebxNEmxg95HQaBk58deGQSwQhxO4AwEu', '강감찬', 'user3@example.com', '01098765432', '67890', 'Another Street', 'Apt 303', NOW(), NOW());

-- Terms 테이블 초기 데이터
INSERT INTO terms (title, content, is_required, version, created_at, updated_at)
VALUES
    ('이용약관 동의', '서비스 이용에 대한 약관입니다.', 1, '1.0', NOW(), NOW()),
    ('개인정보 수집 이용 동의(필수)', '개인정보 보호에 대한 정책입니다.', 1, '1.0', NOW(), NOW()),
    ('개인정보 수집 이용 동의(선택)', '개인정보 보호에 대한 정책입니다.', 0, '1.0', NOW(), NOW()),
    ('마케팅 정보 활용 동의', '마케팅 정보를 활용하는 약관입니다.', 0, '1.0', NOW(), NOW()),
    ('만 14세 이상', '본인은 만 14세 이상입니다.', 1, '1.0', NOW(), NOW());

-- UserAgreement 테이블 초기 데이터
INSERT INTO user_agreement (user_id, terms_id, agreed, created_at, updated_at)
VALUES
    (1, 1, 1, NOW(), NOW()), -- user1 동의
    (1, 2, 1, NOW(), NOW()), -- user1 동의
    (2, 1, 1, NOW(), NOW()), -- user2 동의
    (2, 3, 0, NOW(), NOW()), -- user2 선택 약관 미동의
    (3, 1, 1, NOW(), NOW()), -- user3 동의
    (3, 2, 1, NOW(), NOW()), -- user3 동의
    (3, 4, 1, NOW(), NOW()); -- user3 선택 약관 동의
