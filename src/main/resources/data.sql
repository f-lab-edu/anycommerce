-- Users 테이블 초기 데이터
INSERT INTO users (user_id, password, username, email, phone_number, zipcode, street_address, detail_address, created_at, updated_at)
VALUES
    ('user1', '$2a$10$7sP8JH3rTpKH0.K/ePMrCebxNEmxg95HQaBk58deGQSwQhxO4AwEu', '홍길동', 'user1@example.com', '01012345678', '12345', 'Main Street', 'Apt 101', NOW(), NOW()),
    ('user2', '$2a$10$7sP8JH3rTpKH0.K/ePMrCebxNEmxg95HQaBk58deGQSwQhxO4AwEu', '이순신', 'user2@example.com', '01087654321', '54321', 'Sub Street', 'Apt 202', NOW(), NOW()),
    ('user3', '$2a$10$7sP8JH3rTpKH0.K/ePMrCebxNEmxg95HQaBk58deGQSwQhxO4AwEu', '강감찬', 'user3@example.com', '01098765432', '67890', 'Another Street', 'Apt 303', NOW(), NOW());

-- Terms 테이블 초기 데이터 (복합 키 필드를 고려)
INSERT INTO terms (title, version, content, is_required, is_active, created_at, updated_at)
VALUES
    ('이용약관 동의', 1, '서비스 이용에 대한 약관입니다.', 1, 1, NOW(), NOW()),
    ('개인정보 수집 이용 동의(필수)', 1, '개인정보 보호에 대한 정책입니다.', 1, 1, NOW(), NOW()),
    ('개인정보 수집 이용 동의(선택)', 1, '개인정보 보호에 대한 정책입니다.', 0, 1, NOW(), NOW()),
    ('마케팅 정보 활용 동의', 1, '마케팅 정보를 활용하는 약관입니다.', 0, 0, NOW(), NOW()), -- 비활성화된 약관
    ('만 14세 이상', 1, '본인은 만 14세 이상입니다.', 1, 1, NOW(), NOW()),
    ('개인정보 수집 이용 동의(필수)', 2, '개인정보 보호에 대한 정책입니다. (필수사항입니다.)', 1, 1, NOW(), NOW()),
    ('개인정보 수집 이용 동의(선택)', 2, '개인정보 보호에 대한 정책입니다. (선택사항입니다.)', 0, 1, NOW(), NOW()),
    ('개인정보보호법 관련 신규 약관', 1, '새로운 약관입니다.', 1, 1, NOW(), NOW());

-- UserAgreement 테이블 초기 데이터 (복합 키 구조에 맞춤)
INSERT INTO user_agreement (user_id, terms_title, terms_version, agreed, created_at, updated_at)
VALUES
    (1, '이용약관 동의', 1, true, NOW(), NOW()),
    (1, '개인정보 수집 이용 동의(필수)', 1, true, NOW(), NOW()),
    (2, '이용약관 동의', 1, true, NOW(), NOW()),
    (2, '개인정보 수집 이용 동의(선택)', 1, false, NOW(), NOW()),
    (3, '이용약관 동의', 1, true, NOW(), NOW()),
    (3, '개인정보 수집 이용 동의(필수)', 1, true, NOW(), NOW()),
    (3, '마케팅 정보 활용 동의', 1, true, NOW(), NOW());


INSERT INTO category (id, name, category_code, depths) VALUES
(1, 'Electronics', '001', 0),
(2, 'Books', '002', 0);

INSERT INTO product (id, name, price, discount_price, stock_quantity, product_code, category_id)
VALUES (1, 'Laptop', 1000, 900, 10, '001-001', 1);
