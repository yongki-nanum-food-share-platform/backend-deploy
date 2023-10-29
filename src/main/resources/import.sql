-- 모든 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;
truncate table user_tb;
truncate table brand_tb;
truncate table shop_tb;
truncate table menu_tb;
truncate table option_tb;
truncate table post_tb;
truncate table review_tb;
truncate table cart_tb;
truncate table order_tb;
truncate table item_tb;
truncate table delivery_tb;
truncate table address_tb;
truncate table share_tb;
truncate table notice_tb;
truncate table chat_tb;
truncate table message_tb;
truncate table member_tb;
SET REFERENTIAL_INTEGRITY TRUE;
-- 모든 제약 조건 활성화

-- 어드민 유저
INSERT INTO user_tb (`idx`,`user_id`,`user_name`,`password`,`unregist`, `role`, `create_at`) VALUES ('1', 'rhalstjr1999', 'MinseokGo', '{bcrypt}$2a$10$IzO1kAzNob.oxB0UShJtTOCkkn3xi5ZuEbv8L1AAwDNEeFNQsEJ0m', 'N', 'ADMIN', '2023-08-29 13:54:19.823');
INSERT INTO user_tb (`idx`,`user_id`,`user_name`,`password`,`unregist`, `role`, `create_at`) VALUES ('2', 'alstjr1999', '굽네사장', '{bcrypt}$2a$10$IzO1kAzNob.oxB0UShJtTOCkkn3xi5ZuEbv8L1AAwDNEeFNQsEJ0m', 'N', 'SHOPPER', '2023-08-29 13:54:19.823');
INSERT INTO user_tb (`idx`,`user_id`,`user_name`,`password`,`unregist`, `role`, `create_at`) VALUES ('3', 'alstjr12', '안한주', '{bcrypt}$2a$10$IzO1kAzNob.oxB0UShJtTOCkkn3xi5ZuEbv8L1AAwDNEeFNQsEJ0m', 'N', 'USER', '2023-08-29 13:54:19.823');
INSERT INTO user_tb (`idx`,`user_id`,`user_name`,`password`,`unregist`, `role`, `create_at`) VALUES ('4', 'test', 'admin', '{bcrypt}$2a$10$9GKtBf/GS2xSpPaopBrEYOybbq3/PmN8q8TH9XortBCc6g0XwzRnq', 'N', 'USER', '2023-08-29 13:54:19.823');
INSERT INTO user_tb (`idx`,`user_id`,`user_name`,`password`,`unregist`, `role`, `create_at`) VALUES ('5', 'alstjr6774', '굽네사장님2', '{bcrypt}$2a$10$IzO1kAzNob.oxB0UShJtTOCkkn3xi5ZuEbv8L1AAwDNEeFNQsEJ0m', 'N', 'SHOPPER', '2023-08-29 13:54:19.823');

-- INIT 주소
INSERT INTO address_tb (`idx`, `address`, `user_idx`) VALUES ('1', '부산 동래구 안락동 459-50', '3');

-- INIT 브랜드
INSERT INTO brand_tb (`idx`, `brand_name`, `image`) VALUES ('1', '굽네치킨', '/images/1.jpg');

-- INIT 가게
INSERT INTO shop_tb (`idx`, `shop_name`, `shop_address`, `star_point`, `review_count`, `order_count`, `unregist`, `tip`, `user_idx`, `brand_idx`, `description`) VALUES ('1', '굽네치킨 부산안락점', '부산광역시 동래구 안락동', '5.0', '3', '3', 'N', '3000', '2', '1', '굽네치킨 원조 부산 안락점입니다.');
INSERT INTO shop_tb (`idx`, `shop_name`, `shop_address`, `star_point`, `review_count`, `order_count`, `unregist`, `tip`, `user_idx`, `brand_idx`, `description`) VALUES ('2', '굽네치킨 부산연산점', '부산광역시 연제구 연산동', '0.0', '0', '0', 'N', '3000', '5', '1', '안녕하세요. 맛있게 드시고 가세요.');

-- INIT 메뉴
INSERT INTO menu_tb (`idx`, `menu_name`, `description`, `brand_idx`) VALUES ('1', '고추바사삭', '고추를 잘개 부수어 토핑하고 오븐에 갓 구워낸 바삭함 지존의 고추바사삭', '1');
INSERT INTO menu_tb (`idx`, `menu_name`, `description`, `brand_idx`) VALUES ('2', '볼케이노', '지금까지 이런 맛은 없었다. 불닭과 오븐 구이의 만남! 볼케이노', '1');
INSERT INTO menu_tb (`idx`, `menu_name`, `description`, `brand_idx`) VALUES ('3', '굽네 오리지널', '여태까지의 오븐 치킨은 저리 가라! 굽네만의 스페셜 오븐 구이! 굽네 오리지널', '1');

-- INIT 옵션
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('1', '뼈치킨', '19000', '1');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('2', '순살치킨', '21000', '1');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('3', '고블링 소스 추가', '500', '1');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('4', '마블링 소스 추가', '500', '1');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('5', '콜라 라지 변경', '2000', '1');

INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('6', '뼈치킨', '19000', '2');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('7', '순살치킨', '21000', '2');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('8', '고블링 소스 추가', '500', '2');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('9', '마블링 소스 추가', '500', '2');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('10', '콜라 라지 변경', '2000', '2');

INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('11', '뼈치킨', '17000', '3');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('12', '순살치킨', '19000', '3');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('13', '고블링 소스 추가', '500', '3');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('14', '마블링 소스 추가', '500', '3');
INSERT INTO option_tb (`idx`, `option_name`, `price`, `menu_idx`) VALUES ('15', '콜라 라지 변경', '2000', '3');

-- INIT 주문
INSERT INTO order_tb (`idx`, `user_idx`, `cancel`, `order_name`) VALUES ('1', '3', 'N', '고추바사삭 외 2건');
INSERT INTO order_tb (`idx`, `user_idx`, `cancel`, `order_name`) VALUES ('2', '3', 'Y', '고추바사삭 외 0건');
INSERT INTO order_tb (`idx`, `user_idx`, `cancel`, `order_name`) VALUES ('3', '3', 'N', '볼케이노 외 0건');

-- INIT 주문 아이템
INSERT INTO item_tb (`idx`, `quantity`, `price`, `create_at`, `order_idx`, `option_idx`, `shop_idx`) VALUES ('1', '10', '190000', '2023-08-29 13:54:19.823', '1', '1', '1');
INSERT INTO item_tb (`idx`, `quantity`, `price`, `create_at`, `order_idx`, `option_idx`, `shop_idx`) VALUES ('4', '10', '20000', '2023-08-29 13:54:19.823', '1', '5', '1');
INSERT INTO item_tb (`idx`, `quantity`, `price`, `create_at`, `order_idx`, `option_idx`, `shop_idx`) VALUES ('5', '5', '95000', '2023-08-29 13:54:19.823', '1', '6', '1');
INSERT INTO item_tb (`idx`, `quantity`, `price`, `create_at`, `order_idx`, `option_idx`, `shop_idx`) VALUES ('2', '1', '16000', '2023-08-29 13:54:19.823', '2', '2', '1');
INSERT INTO item_tb (`idx`, `quantity`, `price`, `create_at`, `order_idx`, `option_idx`, `shop_idx`) VALUES ('3', '1', '16000', '2023-08-30 13:54:19.823', '3', '6', '1');


-- INIT 배달
INSERT INTO delivery_tb (`idx`, `address`, `status`, `order_idx`, `user_idx`) VALUES ('1', '연산동', 'Y', '1', '3');
INSERT INTO delivery_tb (`idx`, `address`, `status`, `order_idx`, `user_idx`) VALUES ('3', '연산동', 'N', '2', '3');
INSERT INTO delivery_tb (`idx`, `address`, `status`, `order_idx`, `user_idx`) VALUES ('2', '연산동', 'N', '3', '3');

INSERT INTO review_tb (`idx`, `content`, `star_point`, `create_at`, `user_idx`, `shop_idx`, `order_idx`, `delete`) VALUES ('1', '고추바사삭 잘 먹었습니다.', '5.0', '2023-08-29 13:54:19.823', '3', '1', '1', 'N');
INSERT INTO review_tb (`idx`, `content`, `star_point`, `create_at`, `user_idx`, `shop_idx`, `order_idx`, `delete`) VALUES ('2', '순살 볼케이노는 진리네요.', '5.0', '2023-08-29 13:54:19.823', '3', '1', '1', 'N');
INSERT INTO review_tb (`idx`, `content`, `star_point`, `create_at`, `user_idx`, `shop_idx`, `order_idx`, `delete`) VALUES ('3', '굽네 오리지널은 좀 물리면서도 굉장히 맛있습니다.', '5.0', '2023-08-29 13:54:19.823', '3', '1', '1', 'N');

-- INIT POST
INSERT INTO post_tb (`idx`, `title`, `content`, `time`, `place`, `people`, `create_at`, `delete`, `user_idx`, `shop_idx`) VALUES ('1', '아 오늘같이 더운날 볼케이노로 더위 싹 날리시죠!!', '테스트 게시물입니다.', '17시', '부산 동래구 안락동 459-50', '2', '2023-08-29 13:54:19.823', 'N', '3', '1');
INSERT INTO post_tb (`idx`, `title`, `content`, `time`, `place`, `people`, `create_at`, `delete`, `user_idx`, `shop_idx`) VALUES ('2', '굽네치킨 드실분!!', '테스트 게시물입니다.', '17시', '부산 동래구 안락동 459-50', '2', '2023-08-29 13:54:19.823', 'N', '3', '1');

INSERT INTO share_tb (`idx`, `quantity`, `post_idx`, `option_idx`) VALUES ('1', '5', '1', '1');
INSERT INTO share_tb (`idx`, `quantity`, `post_idx`, `option_idx`) VALUES ('2', '1', '1', '6');
INSERT INTO share_tb (`idx`, `quantity`, `post_idx`, `option_idx`) VALUES ('3', '5', '2', '1');
INSERT INTO share_tb (`idx`, `quantity`, `post_idx`, `option_idx`) VALUES ('4', '1', '2', '6');

INSERT INTO cart_tb (`idx`, `quantity`, `user_idx`, `option_idx`, `shop_idx`) VALUES ('1', '5', '3', '1', '1');
INSERT INTO cart_tb (`idx`, `quantity`, `user_idx`, `option_idx`, `shop_idx`) VALUES ('2', '4', '3', '3', '1');
INSERT INTO cart_tb (`idx`, `quantity`, `user_idx`, `option_idx`, `shop_idx`) VALUES ('3', '3', '3', '4', '1');
INSERT INTO cart_tb (`idx`, `quantity`, `user_idx`, `option_idx`, `shop_idx`) VALUES ('4', '2', '3', '6', '1');
INSERT INTO cart_tb (`idx`, `quantity`, `user_idx`, `option_idx`, `shop_idx`) VALUES ('5', '1', '3', '7', '1');

INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('1', '안녕하세요', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('2', '연안교가 물에 잠겼어요', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('3', '여름 언제 끝날까요ㅠㅠ', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('4', '내일 코딩 테스트 치러 갑니다! 응원 좀요!', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('5', '저녁에 같이 치맥하실분!', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('6', '집오는 길에 맛있는 치킨집을 봤어요. 같이 드실분?', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('7', '음.. 날씨가 너무 덥네요', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('8', '오늘은 정말 날씨가 더워여', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('9', '비가 좀 내렸으면.. 태풍은 싫고..', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('10', '오늘 축구 보시는분?', '2023-08-29 13:54:19.823', 3);
INSERT INTO notice_tb (`idx`, `content`, `create_at`, `user_idx`) VALUES ('11', '동래고등학교 앞에서 소리지르지 마세요.', '2023-08-29 13:54:19.823', 3);

INSERT INTO chat_tb (`idx`, `title`, `create_at`, `delete`, `post_idx`) VALUES ('1', '`굽네치킨 드실분!!` 게시물의 채팅방', '2023-08-29 13:54:19.823', 'N', '2');
INSERT INTO chat_tb (`idx`, `title`, `create_at`, `delete`, `post_idx`) VALUES ('2', '`테스트 포스트` 게시물의 채팅방', '2023-08-29 13:54:19.823', 'N', '1');

INSERT INTO member_tb (`idx`, `user_idx`, `chat_idx`) VALUES ('1', '2', '1');
INSERT INTO member_tb (`idx`, `user_idx`, `chat_idx`) VALUES ('2', '3', '1');
INSERT INTO member_tb (`idx`, `user_idx`, `chat_idx`) VALUES ('3', '1', '1');
INSERT INTO member_tb (`idx`, `user_idx`, `chat_idx`) VALUES ('4', '2', '2');
INSERT INTO member_tb (`idx`, `user_idx`, `chat_idx`) VALUES ('5', '1', '2');

INSERT INTO message_tb (`idx`, `content`, `create_at`, `user_idx`, `chat_idx`) VALUES ('1', '안녕하세요', '2023-08-29 13:54:19.823', '1', '1');
INSERT INTO message_tb (`idx`, `content`, `create_at`, `user_idx`, `chat_idx`) VALUES ('2', '네, 안녕하세요', '2023-08-29 13:54:19.823', '2', '1');
INSERT INTO message_tb (`idx`, `content`, `create_at`, `user_idx`, `chat_idx`) VALUES ('3', '다들 반갑습니다', '2023-08-29 13:54:19.823', '3', '1');
INSERT INTO message_tb (`idx`, `content`, `create_at`, `user_idx`, `chat_idx`) VALUES ('4', '테스트 채팅방 맞죠?', '2023-08-29 13:54:19.823', '2', '2');
INSERT INTO message_tb (`idx`, `content`, `create_at`, `user_idx`, `chat_idx`) VALUES ('5', '맞겠냐고', '2023-08-29 13:54:19.823', '1', '2');

