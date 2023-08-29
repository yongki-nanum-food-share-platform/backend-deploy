-- 모든 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;
truncate table user_tb;
truncate table brand_tb;
truncate table shop_tb;
truncate table menu_tb;
truncate table option_tb;
truncate table post_tb;
truncate table review_tb;
SET REFERENTIAL_INTEGRITY TRUE;
-- 모든 제약 조건 활성화

-- 어드민 유저
INSERT INTO user_tb (`idx`,`user_id`,`user_name`,`password`,`unregist`, `role`, `create_at`) VALUES ('1', 'rhalstjr1999', 'MinseokGo', '{bcrypt}$2a$10$IzO1kAzNob.oxB0UShJtTOCkkn3xi5ZuEbv8L1AAwDNEeFNQsEJ0m', 'Y', 'ADMIN', '2023-08-29 13:54:19.823');
INSERT INTO user_tb (`idx`,`user_id`,`user_name`,`password`,`unregist`, `role`, `create_at`) VALUES ('2', 'alstjr1999', '굽네사장', '{bcrypt}$2a$10$IzO1kAzNob.oxB0UShJtTOCkkn3xi5ZuEbv8L1AAwDNEeFNQsEJ0m', 'Y', 'SHOPPER', '2023-08-29 13:54:19.823');
INSERT INTO user_tb (`idx`,`user_id`,`user_name`,`password`,`unregist`, `role`, `create_at`) VALUES ('3', 'alstjr12', '악성리뷰러', '{bcrypt}$2a$10$IzO1kAzNob.oxB0UShJtTOCkkn3xi5ZuEbv8L1AAwDNEeFNQsEJ0m', 'Y', 'USER', '2023-08-29 13:54:19.823');

-- INIT 브랜드
INSERT INTO brand_tb (`idx`, `brand_name`, `image`) VALUES ('1', '굽네치킨', '/images/1.jpg');

-- INIT 가게
INSERT INTO shop_tb (`idx`, `shop_name`, `shop_address`, `star_point`, `order_count`, `unregist`, `tip`, `user_idx`, `brand_idx`) VALUES ('1', '굽네치킨 부산안락점', '부산광역시 동래구 안락동', '5.0', '3', 'N', '3000', '2', '1');

-- INIT 메뉴
INSERT INTO menu_tb (`idx`, `menu_name`, `description`, `brand_idx`) VALUES ('1', '고추바사삭', '고추를 잘개 부수어 토핑하고 오븐에 갓 구워낸 바삭함 지존의 고추바사삭', '1');
INSERT INTO menu_tb (`idx`, `menu_name`, `description`, `brand_idx`) VALUES ('2', '볼게이노', '지금까지 이런 맛은 없었다. 불닭과 오븐 구이의 만남! 볼케이노', '1');
INSERT INTO menu_tb (`idx`, `menu_name`, `description`, `brand_idx`) VALUES ('3', '굽네 오리지널', '여태까지의 오븐 치킨은 저리 가라! 굽네만의 스페셜 오븐 구이! 굽네 오리지널', '1');

-- INIT 옵션
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('1', '고추바사삭 뼈치킨', '19000', '1');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('2', '고추바사삭 순살치킨', '21000', '1');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('3', '고블링 소스 추가', '500', '1');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('4', '마블링 소스 추가', '19000', '1');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('5', '콜라 라지 변경', '2000', '1');

INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('6', '볼케이노 뼈치킨', '19000', '2');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('7', '볼케이노 순살치킨', '21000', '2');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('8', '고블링 소스 추가', '500', '2');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('9', '마블링 소스 추가', '19000', '2');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('10', '콜라 라지 변경', '2000', '2');

INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('11', '굽네 오리지널 뼈치킨', '17000', '3');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('12', '굽네 오리지널 순살치킨', '19000', '3');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('13', '고블링 소스 추가', '500', '3');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('14', '마블링 소스 추가', '19000', '3');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('15', '콜라 라지 변경', '2000', '3');

-- INIT 리뷰
INSERT INTO review_tb (`idx`, `content`, `star_point`, `create_at`, `user_idx`, `shop_idx`) VALUES ('1', '고추바사삭 잘 먹었습니다.', '5.0', '2023-08-29 13:54:19.823', '3', '1');
INSERT INTO review_tb (`idx`, `content`, `star_point`, `create_at`, `user_idx`, `shop_idx`) VALUES ('2', '순살 볼케이노는 진리네요.', '5.0', '2023-08-29 13:54:19.823', '3', '1');
INSERT INTO review_tb (`idx`, `content`, `star_point`, `create_at`, `user_idx`, `shop_idx`) VALUES ('3', '굽네 오리지널은 좀 물리면서도 굉장히 맛있습니다.', '5.0', '2023-08-29 13:54:19.823', '3', '1');