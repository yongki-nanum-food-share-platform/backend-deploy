package yongkinanum.backenddeploy.shop;

import lombok.Getter;
import yongkinanum.backenddeploy.review.Review;

import java.util.List;
import java.util.stream.Collectors;

public class ShopResponse {
    @Getter
    public static class FindDTO {
        private Long idx;
        private String shopName;
        private String shopAddress;
        private String image;
        private Float starPoint;
        private Integer orderCount;
        private Integer reviewCount;
        private List<ReviewDTO> reviews;

        public FindDTO(List<Review> reviews, Shop shop) {
            this.idx = shop.getIdx();
            this.shopName = shop.getShopName();
            this.shopAddress = shop.getShopAddress();
            this.image = shop.getBrand().getImage();
            this.starPoint = shop.getStarPoint();
            this.orderCount = shop.getOrderCount();
            this.reviewCount = shop.getReviewCount();
            this.reviews = reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
        }

        @Getter
        public static class ReviewDTO {
            private Long idx;
            private String content;
            private Float starPoint;
            private String userName;
            private String createAt;

            public ReviewDTO(Review review) {
                this.idx = review.getIdx();
                this.content = review.getContent();
                this.starPoint = review.getStarPoint();
                this.userName = review.getUser().getUserName();
                this.createAt = review.getCreateAt().toString();
            }
        }
    }
}
