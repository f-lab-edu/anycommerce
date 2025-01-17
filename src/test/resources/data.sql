-- Users 테이블 초기 데이터
INSERT INTO users (user_id, password, username, email, phone_number, zipcode, street_address, detail_address, created_at, updated_at)
VALUES
    ('testuser1', '$2a$10$abcdefghijklmnopqrstuvwxyz123456', '김철수', 'testuser1@example.com', '01011112222', '54321', 'Test Street', 'Unit 101', NOW(), NOW()),
    ('testuser2', '$2a$10$abcdefghijklmnopqrstuvwxyz123456', '박영희', 'testuser2@example.com', '01033334444', '65432', 'Sample Road', 'Apt 202', NOW(), NOW()),
    ('testuser3', '$2a$10$abcdefghijklmnopqrstuvwxyz123456', '이지훈', 'testuser3@example.com', '01055556666', '76543', 'Demo Avenue', 'Suite 303', NOW(), NOW());

-- Terms 테이블 초기 데이터
INSERT INTO terms (title, version, content, is_required, is_active, created_at, updated_at)
VALUES
    ('서비스 이용약관', 1, '이 약관은 서비스 이용에 대한 규정입니다.', 1, 1, NOW(), NOW()),
    ('개인정보 처리방침', 1, '개인정보 보호를 위한 정책입니다.', 1, 1, NOW(), NOW()),
    ('위치기반서비스 이용약관', 1, '위치 정보 사용에 대한 약관입니다.', 0, 1, NOW(), NOW()),
    ('마케팅 정보 수신 동의', 1, '마케팅 정보 수신에 대한 동의입니다.', 0, 1, NOW(), NOW()),
    ('연령 확인', 1, '서비스 사용을 위한 연령 확인입니다.', 1, 1, NOW(), NOW()),
    ('개인정보 제3자 제공 동의', 1, '개인정보의 제3자 제공에 대한 동의입니다.', 1, 1, NOW(), NOW()),
    ('커뮤니티 이용규칙', 1, '커뮤니티 서비스 이용에 대한 규칙입니다.', 0, 1, NOW(), NOW()),
    ('결제 서비스 이용약관', 1, '결제 서비스 이용에 대한 약관입니다.', 1, 1, NOW(), NOW());

-- UserAgreement 테이블 초기 데이터
INSERT INTO user_agreement (user_id, terms_title, terms_version, agreed, created_at, updated_at)
VALUES
    (1, '서비스 이용약관', 1, true, NOW(), NOW()),
    (1, '개인정보 처리방침', 1, true, NOW(), NOW()),
    (2, '서비스 이용약관', 1, true, NOW(), NOW()),
    (2, '위치기반서비스 이용약관', 1, false, NOW(), NOW()),
    (3, '서비스 이용약관', 1, true, NOW(), NOW()),
    (3, '개인정보 처리방침', 1, true, NOW(), NOW()),
    (3, '마케팅 정보 수신 동의', 1, true, NOW(), NOW());

-- Category 테이블 초기 데이터
INSERT INTO category (id, name, category_code, depths, parent_id, created_at, updated_at)
VALUES
(4, 'Clothing', '100', 0, NULL, NOW(), NOW()),
(5, 'Food', '200', 0, NULL, NOW(), NOW()),
(6, 'T-Shirts', '101', 1, 1, NOW(), NOW());

-- Product 테이블 초기 데이터
INSERT INTO product (id, name, price, discount_price, discount_percentage, stock_quantity, main_image_url, description, product_code, category_id, comments, created_at, updated_at)
VALUES (10, 'Cotton T-Shirt', 29.99, 24.99, 17, 100, 'http://example.com/tshirt.jpg', 'Comfortable cotton t-shirt', '101-001', 3, 0, NOW(), NOW());

-- Image 테이블 초기 데이터
INSERT INTO image (id, image_url, width, height, description, format, product_id, is_main, created_at, updated_at)
VALUES
    (10, 'http://example.com/tshirt_front.jpg', 800, 600, 'Front view of T-Shirt', 'jpg', 1, true, NOW(), NOW()),
    (11, 'http://example.com/tshirt_back.jpg', 800, 600, 'Back view of T-Shirt', 'jpg', 1, false, NOW(), NOW());

-- ProductCollection 테이블 초기 데이터
INSERT INTO product_collection (id, name, title, sub_title, is_dynamic)
VALUES
    (5, 'Summer Collection', 'Hot Summer Deals', 'Cool clothes for hot days!', false);

-- ProductCollectionItem 테이블 초기 데이터
INSERT INTO product_collection_item (product_collection_id, product_id, display_order)
VALUES
    (5, 10, 2);
